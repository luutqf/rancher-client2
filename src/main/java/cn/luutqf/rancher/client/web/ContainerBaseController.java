package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.constant.Result;
import cn.luutqf.rancher.client.model.JupyterChapter;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface ContainerBaseController<T> {

    @PostMapping
    Object create(T t);

    @DeleteMapping
    Object delete(String id);

    @GetMapping("start")
    Object start(String id);

    @GetMapping("startByName")
    Object startByName(String name);

    @GetMapping("stop")
    Object stop(String id);

    @GetMapping("stopByName")
    Object stopByName(String name);

    @GetMapping
    Object find( String id);

    @GetMapping("logs")
    Object logs(String id);

}
