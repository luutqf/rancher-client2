package cn.luutqf.rancher.client.web.container.chapter.jupyter;

import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import cn.luutqf.rancher.client.service.JupyterService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JupyterController {

    private final JupyterService jupyterService;
    private final ChapterService<Chapter> chapterService;

    @Autowired
    public JupyterController(JupyterService jupyterService, ChapterService<Chapter> chapterService) {
        this.jupyterService = jupyterService;
        this.chapterService = chapterService;
    }

    @PostMapping
    public Object create(@Valid @RequestBody JupyterChapter jupyterChapter) {
        Optional<String> add = jupyterService.createChapter(jupyterChapter);

        if (add.isPresent()) {
            log.info("创建完了");
            return getUrl(add.get());
        }
        return Optional.empty();
    }

    @GetMapping("url")
    public Object getUrl(String id) {
        Optional<String> url = chapterService.findChapterUrl(id);
        log.info("url找完了：{}",url);
        Optional<String> token = jupyterService.getToken(id);
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



}
