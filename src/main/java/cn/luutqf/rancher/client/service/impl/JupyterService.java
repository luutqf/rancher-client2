package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.WebSocketClientUtil;
import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import cn.luutqf.rancher.client.service.TokenService;
import io.rancher.Rancher;
import io.rancher.service.ContainerApi;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import io.rancher.type.ContainerLogs;
import io.rancher.type.HostAccess;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final ChapterService<Chapter> chapterService;


    @Autowired
    public JupyterService(Rancher rancher, ChapterService<Chapter> chapterService) {
        projectApi = rancher.type(ProjectApi.class);
        containerApi = rancher.type(ContainerApi.class);
        this.chapterService = chapterService;
    }

    public String add(JupyterChapter jupyterChapter) {
        Container container = new Container();
        try {
            container.setPorts(Collections.singletonList(jupyterChapter.getTargetPort()));
            container.setDataVolumes(Collections.singletonList("test/" + jupyterChapter.getUsername() + "/" + jupyterChapter.getChapterName() + ":/home/jovyan/work/"));
            container.setVolumeDriver("rancher-nfs");
            RestTemplate restTemplate = new RestTemplate();
            // todo server常量
            Boolean mkdir = restTemplate.getForObject("http://192.168.20.112:8999/create?path=/nfs/test/"
                + jupyterChapter.getUsername() + "/" + jupyterChapter.getChapterName(), Boolean.class);
            System.out.println("创建文件夹：" + mkdir);
            if (null != mkdir && mkdir) {
                Boolean copy = restTemplate.getForObject("http://192.168.20.112:8999/copy?oldPath=/nfs/test/20140101/chapter/Untitled.ipynb&newPath=/nfs/test/"
                    + jupyterChapter.getUsername() + "/" + jupyterChapter.getChapterName() + "/Untitled.ipynb", Boolean.class);
                System.out.println("创建文件操作：" + copy);
            }

            restTemplate.getForObject("http://192.168.20.112:8999/chmod?path=/nfs/test/",Boolean.class);
            // 获取文件
            return getId(container, jupyterChapter, project, projectApi);
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    public String getToken(String id) {
        try {
            ContainerLogs logs = new ContainerLogs();
            HostAccess body = null;
            int count = 100;
            System.out.println("Rancher容器ID：" + id);
            Map dockerContainer = null;
            String containerId = null;
            Container container = null;
            do {
                container = chapterService.find(id);
                Map<String, Object> data = container.getData();
                dockerContainer = (Map) data.get("dockerContainer");
                if (null != dockerContainer) {
                    containerId = dockerContainer.get("Id").toString();
                    break;
                }
                Thread.sleep(count += 100);
            } while (count < count * count);

            while ((null == body) && count < count * count) {
                body = projectApi.logsContainer(project, id, logs).execute().body();
                Thread.sleep(count += 100);
            }

            if (body != null) {
                WebSocketClient webSocketClient = WebSocketClientUtil.getWebSocketClient(body.getUrl() + "?token=" + body.getToken());
                while (!webSocketClient.isClosed() && count < count * count) {
                    Thread.sleep(count += 100);
                }
            } else {
                throw new RancherException(RancherException.JUPYTER_EMPTY);
            }
            return TokenService.map.get(containerId.substring(0, 12));
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
