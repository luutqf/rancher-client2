package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.exception.RancherException;
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
public class VncController implements BaseController<VncChapter>{

    private final VncService vncService;
    private final ChapterService<Chapter> chapterService;

    @Autowired
    public VncController(VncService vncService, ChapterService<Chapter> chapterService) {
        this.vncService = vncService;
        this.chapterService = chapterService;
    }

    public Object create( VncChapter vncChapter) {
        Optional<String> add = vncService.add(vncChapter);
        if(add.isPresent()){
            return chapterService.findUrl(add.get());
        }
        return Optional.empty();
    }

    @Override
    public Object delete(String id) {
        return chapterService.delete(id);
    }

    @Override
    public Object delete(VncChapter chapter) {
        return chapterService.deleteChapter(chapter);
    }

    @Override
    public Object start(String id) {
        return chapterService.start(id);
    }

    @Override
    public Object stop(String id) {
        return chapterService.stop(id);
    }

    @Override
    public Object getUrl(String id) {
        return chapterService.findUrl(id);
    }

    @Override
    public Object find(String id) {
        return chapterService.find(id);
    }

    @Override
    public Object logs(String id) {
        return "";
    }

}
