package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.constant.Result;
import cn.luutqf.rancher.client.model.JupyterChapter;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface BaseController<T> {

    @PostMapping
    Object create(T t);

    @DeleteMapping
    Object delete(String id);

    @PostMapping("delete")
    Object delete(T t);

    @GetMapping("start")
    Object start(String id);

    @GetMapping("stop")
    Object stop(String id);

    @GetMapping("url")
    Object getUrl( String id);

    @GetMapping
    Object find( String id);

    @GetMapping("logs")
    Object logs(String id);

}
