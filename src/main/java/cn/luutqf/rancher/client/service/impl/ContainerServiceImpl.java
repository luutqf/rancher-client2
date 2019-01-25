package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.entity.MyContainer;
import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.service.ContainerService;
import io.rancher.Rancher;
import io.rancher.base.TypeCollection;
import io.rancher.service.ContainerApi;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import io.rancher.type.InstanceStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

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
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new RancherException(RancherException.CREATE_CONTAINER_ERROR);
        }
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
    public Object stop(String id) {
        return projectApi.stopContainer(project, id, new InstanceStop(false, new BigInteger("0")));
    }

    @Override
    public Object logs(String id) {
        //TODO 获取日志
        return Optional.empty();
    }

    @Override
    public Optional<Container> findById(String id) {
        try {
            return Optional.ofNullable(projectApi.findContainerById(project,id).execute().body());
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
            if (c.getName().equals(name)) {
                return Optional.ofNullable(c);
            }
        }
        return Optional.empty();
    }
}
