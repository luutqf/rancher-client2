package cn.luutqf.rancher.client.service;


import java.util.Optional;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description:
 */
public interface ContainerService<T> {
    Optional<String> add(T t);

    Object delete(String id);

    Object start(String id);

    Object stop(String id);
}
