package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.service.ChapterService;
import io.rancher.type.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: ZhenYang
 * @date: 2019/1/17
 * @description:
 */
@RestController
@RequestMapping("chapter")
public class ChapterController implements BaseController<Chapter> {

    private final ChapterService<Chapter> chapterService;

    @Autowired
    public ChapterController(ChapterService<Chapter> chapterService) {
        this.chapterService = chapterService;
    }

    public Object start(String id) {
        return chapterService.start(id);
    }

    public Object stop(String id) {
        return chapterService.stop(id);
    }

    public Object getUrl(String id) {
        String url = null;
        int count = 10;
        do {
            url = chapterService.findUrl(id);
            try {
                Thread.sleep(count+=10);
            } catch (InterruptedException e) {
                throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
            }
        }while (null==url&&count<10000);
        return url;
    }
    public Object find(String id) {
        return chapterService.find(id);
    }

    public Object delete(String id) {
        return chapterService.delete(id);
    }

    @PostMapping("delete")
    public Object delete(Chapter chapter){
        System.out.println(chapter.getUsername());
        return chapterService.deleteChapter(chapter);
    }
}
