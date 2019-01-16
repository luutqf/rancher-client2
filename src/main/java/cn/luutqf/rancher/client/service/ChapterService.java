package cn.luutqf.rancher.client.service;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;

import java.lang.reflect.Field;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface  ChapterService<T extends Chapter> {

    Object add(T t);

    Object delete(T t);

    Object start(T t);

    Object stop(T t);

    default  String getContainerName(T t) {
        try {
            Class<?> aClass = t.getClass().getSuperclass();
            Field usernameF = aClass.getDeclaredField("username");
            Field imageF = aClass.getDeclaredField("image");
            imageF.setAccessible(true);
            usernameF.setAccessible(true);
            return usernameF.get(t).toString() + "-" + imageF.get(t).toString().substring(imageF.get(t).toString().indexOf("/") + 1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RancherException(e.getMessage(),RancherException.CHAPTER_ERROR);
        }
    }
}
