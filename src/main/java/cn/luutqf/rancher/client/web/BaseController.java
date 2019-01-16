package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.constant.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface BaseController<T> {

    @PostMapping
    Object create(T t);

    @DeleteMapping
    Object delete(T t);

    @PutMapping("start")
    Object start(T t);

    @PutMapping("stop")
    Object stop(T t);
}
