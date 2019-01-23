package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import io.rancher.type.Container;
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

    private final ChapterService<JupyterChapter> jupyterService;
    private final ChapterService<Chapter> chapterService;

    @Autowired
    public JupyterController(ChapterService<JupyterChapter> jupyterService, ChapterService<Chapter> chapterService) {
        this.jupyterService = jupyterService;
        this.chapterService = chapterService;
    }

    public Object create( JupyterChapter jupyterChapter) {
        String id = jupyterService.add(jupyterChapter);
        return getUrl(id);
    }

    @GetMapping("logs")
    public Object logs(String id){
        return jupyterService.getToken(id);
    }


    public Object getUrl(String id) {

        String token = jupyterService.getToken(id);
        String url = chapterService.findUrl(id);
        return url+"/tree/work/Untitled.ipynb"+"?"+token;
    }
    @PostMapping("delete")
    public Object delete(@RequestBody JupyterChapter chapter){
        return chapterService.deleteChapter(chapter);
    }
}
