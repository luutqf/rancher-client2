package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.constant.Result;
import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@RestController
@RequestMapping("jupyter")
public class JupyterController implements BaseController<JupyterChapter>{

    private final ChapterService<JupyterChapter> chapterService;

    @Autowired
    public JupyterController(ChapterService<JupyterChapter> chapterService) {
        this.chapterService = chapterService;
    }

    public Object create(@RequestBody JupyterChapter jupyterChapter) {
        return chapterService.add(jupyterChapter);
    }

    public Object delete(@RequestBody JupyterChapter jupyterChapter) {
        return chapterService.delete(jupyterChapter);
    }

    public Object start(@RequestBody JupyterChapter jupyterChapter) {
        return chapterService.start(jupyterChapter);
    }

    public Object stop(@RequestBody JupyterChapter jupyterChapter) {
        return chapterService.stop(jupyterChapter);
    }
}
