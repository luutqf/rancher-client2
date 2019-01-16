package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import org.springframework.stereotype.Service;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Service
public class JupyterService implements ChapterService<JupyterChapter> {

    public Object add(JupyterChapter jupyterChapter) {
        return "";
    }

    public Object delete(JupyterChapter jupyterChapter) {
        return null;
    }

    public Object start(JupyterChapter jupyterChapter) {
        return null;
    }

    public Object stop(JupyterChapter jupyterChapter) {
        return null;
    }
}
