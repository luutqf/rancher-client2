package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.VncChapter;
import cn.luutqf.rancher.client.service.ChapterAbstract;
import cn.luutqf.rancher.client.service.ChapterService;
import cn.luutqf.rancher.client.service.VncService;
import io.rancher.Rancher;
import io.rancher.base.TypeCollection;
import io.rancher.service.ContainerApi;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import io.rancher.type.ContainerLogs;
import io.rancher.type.InstanceStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import static cn.luutqf.rancher.client.constant.Constants.VNC_PASSWD;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Service
public class VncServiceImpl implements VncService {

    @Value("${rancher.projectId}")
    private String project;

    private final ProjectApi projectApi;

    private final ChapterService<Chapter> chapterService;

    @Autowired
    public VncServiceImpl(Rancher rancher, ChapterService<Chapter> chapterService) {
        projectApi = rancher.type(ProjectApi.class);
        this.chapterService = chapterService;
    }

    public Optional<String> add(VncChapter vncChapter) {
        Container container = new Container();
        container.setPorts(Collections.singletonList(vncChapter.getTargetPort()));
        container.setEnvironment(new LinkedHashMap<String, Object>() {{
            put(VNC_PASSWD, vncChapter.getPassword());
        }});
        return getId(container, vncChapter, project, projectApi);
    }

    @Override
    public Object delete(String id) {
        return chapterService.delete(id);
    }

    @Override
    public Object start(String id) {
        return chapterService.start(id);
    }

    @Override
    public Object stop(String id) {
        return chapterService.stop(id);
    }

    @Override
    public Optional<String> findUrl(String id) {
        return chapterService.findUrl(id);
    }

    @Override
    public Optional<Container> find(String id) {
        return chapterService.find(id);
    }

    @Override
    public Boolean deleteChapter(VncChapter chapter) {
        return chapterService.deleteChapter(chapter);
    }


}
