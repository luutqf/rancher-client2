package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.entity.MyContainer;
import cn.luutqf.rancher.client.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZhenYang
 * @date: 2019/1/18
 * @description:
 */
@RestController
@RequestMapping("container")
public class ContainerController implements ContainerBaseController<MyContainer> {


    private final ContainerService<MyContainer> containerService;

    @Autowired
    public ContainerController(ContainerService<MyContainer> containerService) {
        this.containerService = containerService;
    }

    @Override
    public Object create(MyContainer container) {
        return containerService.add(container);
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
    public Object stop(String id) {
        return containerService.stop(id);
    }

    @Override
    public Object stopByName(String name) {
        return containerService.stopByName(name);
    }

    public Object findByName(String name){
        return containerService.findByName(name);
    }

    @Override
    public Object find(String id) {
        return containerService.findById(id);
    }

    @Override
    public Object logs(String id) {
        return containerService.logs(id);
    }
}
