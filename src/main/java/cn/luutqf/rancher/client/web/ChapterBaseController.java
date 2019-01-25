package cn.luutqf.rancher.client.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: ZhenYang
 * @date: 2019/1/25
 * @description:
 */
public interface ChapterBaseController<T> extends ContainerBaseController<T>{

    @GetMapping("url")
    Object getUrl( String id);

    @PostMapping("delete")
    Object delete(T t);
}
