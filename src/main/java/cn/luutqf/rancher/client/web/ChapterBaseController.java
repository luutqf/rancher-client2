package cn.luutqf.rancher.client.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: ZhenYang
 * @date: 2019/1/25
 * @description:
 */
public interface ChapterBaseController<T> extends ContainerBaseController<T>{

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("url")
    Object getUrl( String id);

    /**
     *
     * @param t
     * @return
     */
    @PostMapping("delete")
    Object delete(T t);

    /**
     *
     * @param t
     * @return
     */
    @PostMapping("stop")
    Object stop(T t);

    /**
     *
     * @param t
     * @return
     */
    @PostMapping("start")
    Object start(T t);
}
