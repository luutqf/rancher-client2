package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import cn.luutqf.rancher.client.service.JupyterService;
import io.rancher.type.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static cn.luutqf.rancher.client.constant.BasicParameter.JupyterDefaultFile;
import static cn.luutqf.rancher.client.constant.BasicParameter.JupyterUrlPrefix;


/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@RestController
@RequestMapping("jupyter")
public class JupyterController implements ChapterBaseController<JupyterChapter> {

    private final JupyterService jupyterService;
    private final ChapterService<Chapter> chapterService;

    @Autowired
    public JupyterController(JupyterService jupyterService, ChapterService<Chapter> chapterService) {
        this.jupyterService = jupyterService;
        this.chapterService = chapterService;
    }

    @Override
    public Object create(@Valid @RequestBody JupyterChapter jupyterChapter) {
        Optional<String> add = jupyterService.createChapter(jupyterChapter);
        if (add.isPresent()) {
            return getUrl(add.get());
        }
        return Optional.empty();
    }

    @Override
    public Object delete(String id) {
        return chapterService.delete(id);
    }

    @Override
    public Object start(String id) {
        return chapterService.stop(id);
    }

    @Override
    public Object startByName(String name) {
        return jupyterService.startByName(name);
    }

    @Override
    public Object stop(String id) {
        return chapterService.stop(id);
    }

    @Override
    public Object stopByName(String name) {
        return jupyterService.stopByName(name);
    }

    public Object logs(String id) {
        return Optional.empty();
    }

    public Object getUrl(String id) {
        Optional<String> token = jupyterService.getToken(id);
        Optional<String> url = chapterService.findChapterUrl(id);
        if (!token.isPresent() || !url.isPresent()) {
            return Optional.empty();
        }
        //todo 文件应与参数一致
        return url.get() + JupyterUrlPrefix +JupyterDefaultFile + "?" + token.get();
    }

    @GetMapping("token")
    public Object getToken(String id) {
        return jupyterService.getToken(id);
    }

    @Override
    public Object find(String id) {
        return chapterService.findById(id);
    }

    @Override
    public Object delete(@RequestBody JupyterChapter chapter) {
        return jupyterService.deleteChapter(chapter);
    }

    @Override
    public Object stop(JupyterChapter jupyterChapter) {
        return null;
    }

    @Override
    public Object start(JupyterChapter jupyterChapter) {
        return null;
    }
}
