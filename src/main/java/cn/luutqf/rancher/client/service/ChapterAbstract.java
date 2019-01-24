package cn.luutqf.rancher.client.service;

import cn.luutqf.rancher.client.model.Chapter;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description:
 */
public abstract class ChapterAbstract<T extends Chapter> implements ChapterService<T> {


    @Override
    public Object delete(String id) {
        return "";
    }

    @Override
    public Object start(String id) {
        return "";
    }

    @Override
    public Object stop(String id) {
        return "";
    }
}
