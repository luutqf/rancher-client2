package cn.luutqf.rancher.client.service;


import cn.luutqf.rancher.client.entity.MyContainer;
import io.rancher.type.Container;

import java.util.Optional;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description:
 */
public interface ContainerService<T> {

    Optional<Container> add(MyContainer myContainer);

    Object delete(String id);

    Object start(String id);

    Object stop(String id);

    Object logs(String id);

    Optional<Container> findById(String id);

    Optional<Container> findByName(String name);
}
