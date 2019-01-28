package cn.luutqf.rancher.client.service;


import cn.luutqf.rancher.client.entity.MyContainer;
import io.rancher.type.Container;

import java.io.IOException;
import java.util.Optional;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description:
 */
public interface ContainerService<T> {

    /**
     *
     * @param myContainer
     * @return
     */
    Optional<Container> add(MyContainer myContainer);

    /**
     *
     * @param id
     * @return
     */
    Object delete(String id);

    /**
     *
     * @param id
     * @return
     */
    Object start(String id);

    /**
     *
     * @param name
     * @return
     */
    Object startByName(String name);

    /**
     *
     * @param name
     * @return
     */
    Object stopByName(String name);

    /**
     *
     * @param id
     * @return
     */
    Object stop(String id);

    /**
     *
     * @param id
     * @return
     */
    String logs(String id) ;

    /**
     *
     * @param id
     * @return
     */
    Optional<Container> findById(String id);

    /**
     *
     * @param name
     * @return
     */
    Optional<Container> findByName(String name);
}
