package cn.luutqf.rancher.client.web;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: ZhenYang
 * @date: 2019/1/17
 * @description:
 */
@RestController
@RequestMapping("chapter")
@Api("chapter api")
public class ChapterController implements ChapterBaseController<Chapter> {

    private final ChapterService<Chapter> chapterService;

    @Autowired
    public ChapterController(ChapterService<Chapter> chapterService) {
        this.chapterService = chapterService;
    }

    @ApiOperation(value = "start chapter")
    public Object start(String id) {
        return chapterService.start(id);
    }

    @ApiOperation(value = "stop chapter")
    public Object stop(String id) {
        return chapterService.stop(id);
    }

    @ApiOperation(value = "get url for chapter")
    public Object getUrl(String id) {
        return chapterService.findChapterUrl(id);
    }

    public Object find(String id) {
        return chapterService.findById(id);
    }

    @Override
    public Object logs(String id) {
        return "";
    }

    @Override
    public Object create(Chapter chapter) {
        return chapterService.createChapter(chapter);
    }

    @Override
    public Object delete(String id) {
        return chapterService.delete(id);
    }

    @Override
    public Object delete(Chapter chapter){
        return chapterService.deleteChapter(chapter);
    }
}
