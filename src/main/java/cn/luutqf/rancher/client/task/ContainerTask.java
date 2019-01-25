package cn.luutqf.rancher.client.task;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.service.ChapterService;
import io.rancher.Rancher;
import io.rancher.base.TypeCollection;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static cn.luutqf.rancher.client.constant.BasicParameter.ContainerTTL;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description:
 */
@Component
@EnableScheduling
@Slf4j
public class ContainerTask {

    @Value("${rancher.projectId}")
    private String project;

    @Value("${rancher.deleteTimeDefault}")
    private Integer deleteTimeDefault;


    private final ProjectApi projectApi;

    private final ChapterService<Chapter> chapterService;

    @Autowired
    public ContainerTask(Rancher rancher, ChapterService<Chapter> chapterService) {
        projectApi = rancher.type(ProjectApi.class);
        this.chapterService = chapterService;
    }

    @Scheduled(cron = " 0/50 * * * * *")
    public void test() throws ParseException {
        TypeCollection<Container> body;
        try {
            body = projectApi.listContainers(project).execute().body();
        } catch (Exception e) {
            throw new RancherException(e.getMessage(), RancherException.LIST_CONTAINER_ERROR);
        }

        if (Optional.ofNullable(body).isPresent()) {
            body.getData().stream().filter(c -> !c.getSystem() && c.getLabels().containsKey(ContainerTTL)).forEach(c -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date parse;
                try {
                    parse = sdf.parse(c.getCreated());
                    long time = (new Date().getTime() - parse.getTime()) / (1000 * 60) - 8 * 60;
                    if (time >= Integer.valueOf(c.getLabels().get(ContainerTTL).toString())) {
                        chapterService.delete(c.getId());
                        log.info("容器{}::{}自动删除", c.getId(),c.getName());
                    } else {
                        log.info("容器{}::{}还剩{}分钟停止", c.getId(),c.getName(), Integer.valueOf(c.getLabels().get(ContainerTTL).toString()) - time);
                    }
                } catch (ParseException e) {
                    throw new RancherException(e.getMessage(), RancherException.TIME_ERROR);
                }
            });
        }
    }
}

