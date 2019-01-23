package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.constant.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface BaseController<T> {

    @PostMapping
    default Object create(T t){ return null; }

    @DeleteMapping
    default Object delete(String id){ return null; }

    @GetMapping("start")
    default Object start(String id){ return null; }

    @GetMapping("stop")
    default Object stop(String id){ return null; }

    @GetMapping("url")
    default Object getUrl( String id){ return null; }

    @GetMapping
    default Object find( String id){ return null; }
}
