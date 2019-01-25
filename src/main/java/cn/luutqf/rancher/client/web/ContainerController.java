package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.entity.MyContainer;
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


    @Override
    public Object create(MyContainer container) {
        return "";
    }
    @Override
    public Object delete(String id) {
        return "";
    }

    @Override
    public Object start(String id) {
        return "";
    }

    @Override
    public Object stop(String id) {
        return "";
    }

    @Override
    public Object find(String id) {
        return "";
    }

    @Override
    public Object logs(String id) {
        return "";
    }
}
