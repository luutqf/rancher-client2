package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.entity.MyContainer;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.model.VncChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import cn.luutqf.rancher.client.service.VncService;
import io.rancher.Rancher;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.util.*;

import static cn.luutqf.rancher.client.constant.BasicParameter.*;
import static cn.luutqf.rancher.client.constant.Constants.CONSOL_VNC_PASSWD;
import static cn.luutqf.rancher.client.constant.Constants.VNC_PASSWD;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Service("VncServiceImpl")
public class VncServiceImpl implements VncService {

    @Value("${rancher.projectId}")
    private String project;

    private final ProjectApi projectApi;

    private final ChapterService<Chapter> chapterService;


    @Autowired
    public VncServiceImpl(Rancher rancher, ChapterService<Chapter> chapterService) {
        projectApi = rancher.type(ProjectApi.class);
        this.chapterService = chapterService;
    }

    public Optional<String> createChapter(VncChapter vncChapter) {
        System.out.println(vncChapter.getImage());
        Optional<String> chapterContainerName = getChapterContainerName(vncChapter);
        MyContainer build = MyContainer.builder()
//            .ports(Arrays.asList(vncChapter.getTargetPort()))
//            .labels(new LinkedHashMap<String, Object>() {{
//                put(ContainerTTL, vncChapter.getTtl());
//            }})
            .ttl(vncChapter.getTtl())
            .environment(new LinkedHashMap<String, Object>() {{
                put(VNC_PASSWD, vncChapter.getPassword());
                put(CONSOL_VNC_PASSWD, vncChapter.getPassword());
                put("VNC_RESOLUTION", "1280x720");
            }})
            .imageUuid(vncChapter.getContainerType() + vncChapter.getImage())
            .build();
        chapterContainerName.ifPresent(build::setName);
        List<String> ports = new ArrayList<>();
        if(Optional.ofNullable(vncChapter.getTargetPort()).isPresent()){
            ports.add(vncChapter.getTargetPort());
        }else{
            ports.add("80");
        }
        if(Optional.ofNullable(vncChapter.getPorts()).isPresent()){
            if (!vncChapter.getPorts().isEmpty()){
                ports.addAll(vncChapter.getPorts());
            }
        }
        build.setPorts(ports);
        return add(build).map(Container::getId);
    }


    @Override
    public Optional<Container> add(MyContainer myContainer) {
        return chapterService.add(myContainer);
    }

    @Override
    public Object delete(String id) {
        return chapterService.delete(id);
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
    public Object stopByName(String name) {
        return chapterService.stopByName(name);
    }

    @Override
    public Object stop(String id) {
        return chapterService.stop(id);
    }

    @Override
    public String logs(String id) {
        return chapterService.logs(id);
    }

    @Override
    public Optional<String> findChapterUrl(String id) {
        return chapterService.findChapterUrl(id);
    }

    @Override
    public Optional<Container> findById(String id) {
        return chapterService.findById(id);
    }

    @Override
    public Optional<Container> findByName(String name) {
        return chapterService.findByName(name);
    }

    @Override
    public Boolean deleteChapter(VncChapter chapter) {
        return chapterService.deleteChapter(chapter);
    }

    @Override
    public Optional<String> getChapterContainerName(VncChapter vncChapter) {
        return chapterService.getChapterContainerName(vncChapter);
    }

    @Override
    public Optional<String> getChapterId(Response<Container> execute, VncChapter vncChapter, String project, ProjectApi api) {
        return chapterService.getChapterId(execute,vncChapter,project,projectApi);
    }


}
