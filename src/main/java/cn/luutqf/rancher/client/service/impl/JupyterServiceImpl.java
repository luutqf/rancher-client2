package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.entity.MyContainer;
import cn.luutqf.rancher.client.utils.LogsUtil;
import cn.luutqf.rancher.client.utils.WebSocketClientUtil;
import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.*;
import io.rancher.Rancher;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import io.rancher.type.ContainerLogs;
import io.rancher.type.HostAccess;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;

import static cn.luutqf.rancher.client.constant.BasicParameter.*;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Service("JupyterServiceImpl")
@Slf4j
public class JupyterServiceImpl implements JupyterService {

    @Value("${rancher.projectId}")
    private String project;

    private final ProjectApi projectApi;

    private final FileService fileService;

    private final ChapterService<Chapter> chapterService;


    @Autowired
    public JupyterServiceImpl(Rancher rancher, ChapterService<Chapter> chapterService, FileService fileService) {
        projectApi = rancher.type(ProjectApi.class);
        this.chapterService = chapterService;
        this.fileService = fileService;
    }

    @Override
    public Optional<String> createChapter(JupyterChapter jupyterChapter) {

        Boolean mkdir = fileService.create(NfsPrefix + getFilePath(jupyterChapter));
        log.info("创建文件夹：{}", mkdir);
        if (Optional.ofNullable(mkdir).isPresent() && mkdir) {
            Boolean copy = fileService.copy(jupyterChapter.getFile(), NfsPrefix + getFilePath(jupyterChapter) + "/" + JupyterDefaultFile);
            log.info("创建文件操作：{}", copy);
        }
        fileService.chmod(NfsPrefix);

        Optional<String> chapterContainerName = getChapterContainerName(jupyterChapter);
        MyContainer build = MyContainer.builder()
            .ports(Collections.singletonList(jupyterChapter.getTargetPort()))
            .labels(new LinkedHashMap<String, Object>() {{
                put(ContainerTTL, jupyterChapter.getTtl());
            }})
            .dataVolumes(Collections.singletonList(NfsWorkSpace + getFilePath(jupyterChapter) + ":" + JupyterWorkSpace))
            .volumeDriver(RancherVolumeDriver)
            .imageUuid(jupyterChapter.getContainerType() + jupyterChapter.getImage())
            .build();
        chapterContainerName.ifPresent(build::setName);
        Optional<String> s = add(build).map(Container::getId);

        return s;
    }


    @Override
    public Optional<Container> add(MyContainer myContainer) {
        return chapterService.add(myContainer);
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
    public Object logs(String id) {
        return null;
    }

    @Override
    public Optional<String> getToken(String id) {
        try {
            ContainerLogs logs = new ContainerLogs();
            HostAccess body;
            int count = MinRequestTime;
            Map dockerContainer;
            String containerId = "";
            Container container;

            log.info("Rancher container ID：{}", id);
            //todo 函数式编程
            for (int i = MinRequestTime; i < MaxRequestTime; i += i) {
                Optional<Container> container1 = chapterService.findById(id);
                if (!container1.isPresent()) {
                    Thread.sleep(i);
                    continue;
                }
                container = container1.get();
                Map<String, Object> data = container.getData();
                dockerContainer = (Map) data.get("dockerContainer");
                if (Optional.ofNullable(dockerContainer).isPresent()) {
                    containerId = dockerContainer.get("Id").toString();
                    log.info("docker container ID：{}", containerId);
                    break;
                }
                Thread.sleep(i);
            }

            if (StringUtils.isEmpty(containerId))
                throw new RancherException(RancherException.JUPYTER_EMPTY);

            do {
                body = projectApi.logsContainer(project, id, logs).execute().body();
                Thread.sleep(count += count);
            } while (!Optional.ofNullable(body).isPresent() && count < MaxRequestTime);

            if (Optional.ofNullable(body).isPresent()) {
                WebSocketClient webSocketClient;
                Optional<WebSocketClient> optional = WebSocketClientUtil.getWebSocketClient(body.getUrl() + "?token=" + body.getToken(), LogsUtil.getTokenByRegex);
                if (optional.isPresent()) {
                    webSocketClient = optional.get();
                } else {
                    throw new RancherException(RancherException.WEB_SOCKET_ERROR);
                }
                while (!webSocketClient.isClosed() && count < MaxRequestTime) {
                    Thread.sleep(count += count);
                }
            } else {
                throw new RancherException(RancherException.JUPYTER_EMPTY);
            }
            //todo 因为缓存的生命周期与应用同步，所以可以转移
            return Optional.ofNullable(LogsUtil.tokenMap.remove(containerId.substring(0, 12)));
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        } catch (InterruptedException e) {
            throw new RancherException(e.getMessage(), RancherException.THREAD_ERROR);
        }
    }

    @Override
    public Optional<String> findChapterUrl(String id) {
        return chapterService.findChapterUrl(id);
    }

    @Override
    public Optional<Container> findById(String id) {
        return chapterService.findById(id);
    }

    @Override
    public Optional<Container> findByName(String name) {
        return chapterService.findByName(name);
    }

    @Override
    public Boolean deleteChapter(JupyterChapter chapter) {
        Boolean delete = fileService.delete(NfsPrefix + chapter.getUsername() + "/" + chapter.getChapterName());
        log.info("delete user：{}の{} file:{}", chapter.getUsername(), chapter.getChapterName(), delete);
        return chapterService.deleteChapter(chapter);
    }

    @Override
    public Optional<String> getChapterContainerName(JupyterChapter jupyterChapter) {

        return chapterService.getChapterContainerName(jupyterChapter);
    }

    @Override
    public Optional<String> getChapterId(Response<Container> execute, JupyterChapter jupyterChapter, String project, ProjectApi api) {
        return chapterService.getChapterId(execute, jupyterChapter, project, projectApi);
    }
}
