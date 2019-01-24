package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.JupyterChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import cn.luutqf.rancher.client.service.JupyterService;
import io.rancher.type.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@RestController
@RequestMapping("jupyter")
public class JupyterController implements BaseController<JupyterChapter>{

    private final JupyterService jupyterService;
    private final ChapterService<Chapter> chapterService;

    @Autowired
    public JupyterController(JupyterService jupyterService, ChapterService<Chapter> chapterService) {
        this.jupyterService = jupyterService;
        this.chapterService = chapterService;
    }

    public Object create(@RequestBody JupyterChapter jupyterChapter) {
        Optional<String> add = jupyterService.add(jupyterChapter);
        if (add.isPresent()){
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
    public Object stop(String id) {
        return chapterService.stop(id);
    }

    public Object logs(String id){
        return Optional.empty();
    }


    public Object getUrl(String id) {
        Optional<String> token = jupyterService.getToken(id);

//        String token = .get();
        Optional<String> url = chapterService.findUrl(id);
        if(!token.isPresent()||!url.isPresent()){
            return Optional.empty();
        }
        //todo 文件应与参数一致
        return url.get()+"/tree/work/Untitled.ipynb"+"?"+token.get();
    }

    @GetMapping("token")
    public Object getToken(String id){
        return jupyterService.getToken(id);
    }

    public Object find(String id) {
        return chapterService.find(id);
    }

    public Object delete(@RequestBody JupyterChapter chapter){
        return jupyterService.deleteChapter(chapter);
    }
}
