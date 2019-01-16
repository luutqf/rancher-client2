package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.constant.Result;
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

    private final ChapterService<VncChapter> chapterService;

    @Autowired
    public VncController(ChapterService<VncChapter> chapterService) {
        this.chapterService = chapterService;
    }

    public Object create(@RequestBody VncChapter vncChapter) {
        return chapterService.add(vncChapter);
    }

    public Object delete(@RequestBody VncChapter vncChapter) {
        return chapterService.delete(vncChapter);
    }

    public Object start(@RequestBody VncChapter vncChapter) {
        return chapterService.start(vncChapter);
    }

    public Object stop(@RequestBody VncChapter vncChapter) {
        return chapterService.stop(vncChapter);
    }
}
