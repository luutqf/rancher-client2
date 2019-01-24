package cn.luutqf.rancher.client.service;

import cn.luutqf.rancher.client.model.JupyterChapter;

import java.util.Optional;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description:
 */
public interface JupyterService extends ChapterService<JupyterChapter>{
    Optional<String> getToken(String id);
}
