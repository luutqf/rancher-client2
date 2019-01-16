package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import io.rancher.Rancher;
import io.rancher.service.ContainerApi;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Service
public class JupyterService implements ChapterService<JupyterChapter> {

    @Value("${rancher.projectId}")
    private String project;

    private final ProjectApi projectApi;
    private final ContainerApi containerApi;

    @Autowired
    public JupyterService(Rancher rancher) {
        projectApi = rancher.type(ProjectApi.class);
        containerApi = rancher.type(ContainerApi.class);
    }

    public Object add(JupyterChapter jupyterChapter) {
        try {
            Container container = new Container();
            container.setPorts(Collections.singletonList(jupyterChapter.getTargetPort()));
            container.setDataVolumes(Collections.singletonList("test/" + jupyterChapter.getUsername() + "/" + jupyterChapter.getChapterName() + ":/home/jovyan/work/"));
            container.setVolumeDriver("rancher-nfs");
            container.setImageUuid(jupyterChapter.getContainerType() + jupyterChapter.getImage());
            container.setName(jupyterChapter.getBodyType() + getContainerName(jupyterChapter));
            return projectApi.createContainer(project, container).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    public Object delete(JupyterChapter jupyterChapter) {
        return null;
    }

    public Object start(JupyterChapter jupyterChapter) {
        return null;
    }

    public Object stop(JupyterChapter jupyterChapter) {
        return null;
    }
}
