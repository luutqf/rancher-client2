package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.entity.MyContainer;
import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.service.ChapterService;
import cn.luutqf.rancher.client.service.ContainerService;
import io.rancher.Rancher;
import io.rancher.service.ContainerApi;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.luutqf.rancher.client.constant.BasicParameter.MaxRequestTime;
import static cn.luutqf.rancher.client.constant.BasicParameter.MinRequestTime;

/**
 * @Author: ZhenYang
 * @date: 2019/1/17
 * @description:
 */
@Slf4j
@Service("ChapterServiceImpl")
public class ChapterServiceImpl implements ChapterService<Chapter> {

    @Value("${rancher.projectId}")
    private String project;

    private final ProjectApi projectApi;
    private final ContainerApi containerApi;
    private final ContainerService<MyContainer> containerService;

    @Autowired
    public ChapterServiceImpl(Rancher rancher, ContainerService<MyContainer> containerService) {
        projectApi = rancher.type(ProjectApi.class);
        containerApi = rancher.type(ContainerApi.class);
        this.containerService = containerService;
    }

    @Override
    public Optional<String> createChapter(Chapter chapter) {
        return Optional.empty();
    }


    @Override
    public Optional<Container> add(MyContainer myContainer) {
        return containerService.add(myContainer);
    }

    @Override
    public Object delete(String id) {
        return containerService.delete(id);
    }

    @Override
    public Object start(String id) {
        return containerService.start(id);
    }

    @Override
    public Object startByName(String name) {
        return containerService.startByName(name);
    }

    @Override
    public Object stopByName(String name) {
        return containerService.stopByName(name);
    }

    @Override
    public Object stop(String id) {
        return containerService.stop(id);
    }

    @Override
    public String logs(String id) {
            return containerService.logs(id);
    }

    @Override
    public Optional<String> findChapterUrl(String id) {
        Optional<String> url;
        int i = MinRequestTime;
        do {
            url = findChapterUrlUtil(id);
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
            }
        } while ((i += i) < MaxRequestTime && !url.isPresent());
        return url;
    }

    private Optional<String> findChapterUrlUtil(String id) {
        Optional<Container> container = findById(id);
        Map map = new HashMap();
        List ports = new ArrayList();
        if (container.isPresent()) {
            Map data = container.get().getData();
            if (data.containsKey("fields"))
                map = (Map) data.get("fields");
        }
        if (map.containsKey("ports")) {
            ports = (List) map.get("ports");
        }
        if (!ports.isEmpty()) {
            Pattern p = Pattern.compile(":.?\\d+");
            Matcher m = p.matcher(ports.get(0).toString());
            if (m.find()) {
                if (map.containsKey("dockerHostIp"))
                    return Optional.of("http://" + map.get("dockerHostIp") + m.group(0));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Container> findById(String id) {
        if (StringUtils.isEmpty(id)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(containerApi.findById(id).execute().body());
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    @Override
    public Optional<Container> findByName(String name) {
        return containerService.findByName(name);
    }

    @Override
    public Boolean deleteChapter(Chapter chapter) {
        try {
            Optional<String> chapterContainerName = getChapterContainerName(chapter);
            if (chapterContainerName.isPresent()) {
                Optional<Container> byName = containerService.findByName(chapterContainerName.get());
                if (byName.isPresent())
                    projectApi.deleteContainer(project, byName.get().getId()).execute();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<String> getChapterContainerName(Chapter c) {
        boolean i = StringUtils.isEmpty(c.getUsername());
        boolean k = StringUtils.isEmpty(c.getImage());
        boolean j = StringUtils.isEmpty(c.getChapterName());
        boolean y = StringUtils.isEmpty(c.getSubjectName());
        if (!(i || k || j || y)) {
            return Optional.of(c.getUsername() + "-" + c.getSubjectName() +"-" + c.getChapterName() + "-" + c.getImage().substring(c.getImage().indexOf("/") + 1));
        } else {
            log.warn("Class Field の problem :{}", c.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getChapterId(Response<Container> execute, Chapter chapter, String project, ProjectApi api) {
        if (Objects.requireNonNull(execute).message().startsWith("Unprocessable")) {
            Optional<String> chapterContainerName = getChapterContainerName(chapter);
            if (chapterContainerName.isPresent()) {
                Optional<Container> byName = containerService.findByName(chapterContainerName.get());
                if (byName.isPresent())
                    return Optional.of(byName.get().getId());
            }
        }
        if (Optional.ofNullable(execute.body()).isPresent())
            return Optional.ofNullable(execute.body().getId());
        return Optional.empty();
    }
}
