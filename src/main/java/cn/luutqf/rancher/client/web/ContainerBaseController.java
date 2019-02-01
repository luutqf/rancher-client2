package cn.luutqf.rancher.client.web;

import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface ContainerBaseController<T> {

    /**
     *
     * @param t
     * @return
     */
    @PostMapping
    Object create(T t);

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping
    Object delete(String id);

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("start")
    Object start(String id);

    /**
     *
     * @param name
     * @return
     */
    @GetMapping("startByName")
    Object startByName(String name);

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("stop")
    Object stop(String id);

    /**
     *
     * @param name
     * @return
     */
    @GetMapping("stopByName")
    Object stopByName(String name);

    /**
     *
     * @param id
     * @return
     */
    @GetMapping
    Object find( String id);

    @GetMapping("name")
    Object findByName(String name);

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("logs")
    Object logs(String id);

}
