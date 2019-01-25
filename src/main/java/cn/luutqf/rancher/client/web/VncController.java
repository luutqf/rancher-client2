package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.VncChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import cn.luutqf.rancher.client.service.VncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@RestController
@RequestMapping("vnc")
public class VncController implements ChapterBaseController<VncChapter> {

    private final VncService vncService;
    private final ChapterService<Chapter> chapterService;

    @Autowired
    public VncController(VncService vncService, ChapterService<Chapter> chapterService) {
        this.vncService = vncService;
        this.chapterService = chapterService;
    }

    @Override
    public Object create(@RequestBody VncChapter vncChapter) {
        Optional<String> add = vncService.createChapter(vncChapter);
        if (add.isPresent()) {
            Optional<String> url = chapterService.findChapterUrl(add.get());
            if (url.isPresent()) return url.get();
        }
        return Optional.empty();
    }

    @Override
    public Object delete(String id) {
        return chapterService.delete(id);
    }

    @Override
    public Object delete(@RequestBody VncChapter chapter) {
        return chapterService.deleteChapter(chapter);
    }

    @Override
    public Object stop(VncChapter vncChapter) {
        return null;
    }

    @Override
    public Object start(VncChapter vncChapter) {
        return null;
    }

    @Override
    public Object start(String id) {
        return chapterService.start(id);
    }

    @Override
    public Object startByName(String name) {
        return chapterService.startByName(name);
    }

    @Override
    public Object stop(String id) {
        return chapterService.stop(id);
    }

    @Override
    public Object stopByName(String name) {
        return chapterService.stopByName(name);
    }

    @Override
    public Object getUrl(String id) {
        return chapterService.findChapterUrl(id);
    }

    @Override
    public Object find(String id) {
        return chapterService.findById(id);
    }

    @Override
    public Object logs(String id) {
        return "";
    }

}
