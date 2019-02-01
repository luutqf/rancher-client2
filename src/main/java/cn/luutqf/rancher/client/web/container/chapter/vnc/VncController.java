package cn.luutqf.rancher.client.web.container.chapter.vnc;

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
public class VncController{

    private final VncService vncService;
    private final ChapterService<Chapter> chapterService;

    @Autowired
    public VncController(VncService vncService, ChapterService<Chapter> chapterService) {
        this.vncService = vncService;
        this.chapterService = chapterService;
    }

    @PostMapping
    public Object create(@RequestBody VncChapter vncChapter) {
        Optional<String> add = vncService.createChapter(vncChapter);
        if (add.isPresent()) {
            Optional<String> url = chapterService.findChapterUrl(add.get());
            if (url.isPresent()) return url.get()+"?password="+vncChapter.getPassword();
        }
        return Optional.empty();
    }


}
