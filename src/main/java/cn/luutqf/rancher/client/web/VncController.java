package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.VncChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@RestController
@RequestMapping("vnc")
public class VncController implements BaseController<VncChapter>{

    private final ChapterService<VncChapter> vncService;
    private final ChapterService<Chapter> chapterService;

    @Autowired
    public VncController(ChapterService<VncChapter> vncService, ChapterService<Chapter> chapterService) {
        this.vncService = vncService;
        this.chapterService = chapterService;
    }

    public Object create( VncChapter vncChapter) {
        String id = vncService.add(vncChapter);
        System.out.println(id);
        return chapterService.findUrl(id);
    }

}
