package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.entity.MyContainer;
import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.service.ContainerService;
import cn.luutqf.rancher.client.utils.LogsUtil;
import cn.luutqf.rancher.client.utils.WebSocketClientUtil;
import io.rancher.Rancher;
import io.rancher.base.TypeCollection;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import io.rancher.type.ContainerLogs;
import io.rancher.type.HostAccess;
import io.rancher.type.InstanceStop;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

import static cn.luutqf.rancher.client.constant.BasicParameter.MaxRequestTime;
import static cn.luutqf.rancher.client.constant.BasicParameter.MinRequestTime;

/**
 * @Author: ZhenYang
 * @date: 2019/1/25
 * @description:
 */
@Service("ContainerServiceImpl")
public class ContainerServiceImpl implements ContainerService<MyContainer> {

    @Value("${rancher.projectId}")
    private String project;

    private final ProjectApi projectApi;


    @Autowired
    public ContainerServiceImpl(Rancher rancher) {
        projectApi = rancher.type(ProjectApi.class);
    }

    @Override
    public Optional<Container> add(MyContainer myContainer) {
        Container container = new Container();
        container.setLabels(myContainer.getLabels());
        container.setPorts(myContainer.getPorts());
        container.setName(myContainer.getName());
        container.setImageUuid(myContainer.getImageUuid());
        container.setVolumeDriver(myContainer.getVolumeDriver());
        container.setDataVolumes(myContainer.getDataVolumes());
        container.setEnvironment(myContainer.getEnvironment());
        try {
            Container body = projectApi.createContainer(project, container).execute().body();
            if (!Optional.ofNullable(body).isPresent()) {
                Optional<Container> byName = findByName(myContainer.getName());
                if (Optional.ofNullable(byName).isPresent()) {
                    return byName;
                }
            } else {
                return Optional.of(body);
            }
        } catch (IOException e) {
            throw new RancherException(RancherException.CREATE_CONTAINER_ERROR);
        }
        return Optional.empty();
    }

    @Override
    public Object delete(String id) {
        try {
            return projectApi.deleteContainer(project, id).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.DELETE_ERROR);
        }
    }

    @Override
    public Object start(String id) {
        try {
            return projectApi.startContainer(project, id).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.START_ERROR);
        }
    }

    @Override
    public Object startByName(String name) {
        Optional<Container> byName = findByName(name);
        if (byName.isPresent()) {
            start(byName.get().getId());
            return byName.get();
        }
        return Optional.empty();
    }

    @Override
    public Object stopByName(String name) {
        Optional<Container> byName = findByName(name);
        if (byName.isPresent()) {
            stop(byName.get().getId());
            return byName.get();
        }
        return Optional.empty();
    }

    @Override
    public Object stop(String id) {
        try {
            return projectApi.stopContainer(project, id, new InstanceStop(false, new BigInteger("0"))).execute().isSuccessful();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.STOP_ERROR);
        }
    }

    @Override
    public String logs(String id) {
        int count = MinRequestTime;
        //TODO 获取日志
        Optional<Container> byId = findById(id);
        if (byId.isPresent()) {
            Container container = byId.get();
            ContainerLogs logs = new ContainerLogs();
            HostAccess body;
            try {
                body = projectApi.logsContainer(project, id, logs).execute().body();
            } catch (IOException e) {
                throw new RancherException(e.getMessage(), RancherException.LOG_ERROR);
            }
            LogsUtil.logMap2.put(container.getId(), new StringBuffer());
            if (Optional.ofNullable(body).isPresent()) {
                Optional<WebSocketClient> webSocketClient = WebSocketClientUtil.getWebSocketClient(body.getUrl() + "?token=" + body.getToken(), container.getId());
                if (webSocketClient.isPresent()) {
                    while (!webSocketClient.get().isClosed() && count < MaxRequestTime / 8) {
                        try {
                            Thread.sleep(count += count);
                        } catch (InterruptedException e) {
                            throw new RancherException(e.getMessage(), RancherException.LOG_ERROR);
                        }
                    }
                    webSocketClient.get().close();
                }
            }
            if (LogsUtil.logMap2.containsKey(container.getId())) {
                return LogsUtil.logMap2.get(container.getId()).toString();
            }
        }
        return "";
    }

    @Override
    public Optional<Container> findById(String id) {
        try {
            return Optional.ofNullable(projectApi.findContainerById(project, id).execute().body());
        } catch (IOException e) {
            throw new RancherException(RancherException.LIST_CONTAINER_ERROR);
        }
    }

    @Override
    public Optional<Container> findByName(String name) {
        TypeCollection<Container> body;
        try {
            body = projectApi.listContainers(project).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.LIST_CONTAINER_ERROR);
        }
        if (!Optional.ofNullable(body).isPresent()) {
            return Optional.empty();
        }
        for (Container c : body.getData()) {
            if (Optional.ofNullable(c.getName()).isPresent()) {
                if (c.getName().equals(name)) {
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }
}
