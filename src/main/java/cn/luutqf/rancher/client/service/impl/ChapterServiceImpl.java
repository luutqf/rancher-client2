package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.service.ChapterService;
import io.rancher.Rancher;
import io.rancher.service.ContainerApi;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import io.rancher.type.Instance;
import io.rancher.type.InstanceStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: ZhenYang
 * @date: 2019/1/17
 * @description:
 */
@Service
public class ChapterServiceImpl implements ChapterService<Chapter> {

    @Value("${rancher.projectId}")
    private String project;

    private final ProjectApi projectApi;
    private final ContainerApi containerApi;

    @Autowired
    public ChapterServiceImpl(Rancher rancher) {
        projectApi = rancher.type(ProjectApi.class);
        containerApi = rancher.type(ContainerApi.class);
    }

    public Object delete(String id) {
        try {
            return projectApi.deleteContainer(project, id).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    public Object start(String id) {
        try {
            return containerApi.start(id).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    public Object stop(String id) {
        try {
            return containerApi.stop(id, new InstanceStop(false, new BigInteger("0"))).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }



    @Override
    public String findUrl(String id) {
        String url = null;
        int count = 10;
        do {
            url = findUrlUtil(id);
            try {
                Thread.sleep(count+=10);
            } catch (InterruptedException e) {
                throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
            }
        }while (null==url&&count<10000);
        return url;

    }
    private String findUrlUtil(String id){
        Map map = (Map) Objects.requireNonNull(find(id)).getData().get("fields");
        List ports = (List) map.get("ports");
        Pattern p = Pattern.compile(":.?\\d+");
        Matcher m = p.matcher(ports.get(0).toString());
        if (m.find())
            return "http://" + map.get("dockerHostIp") + m.group(0);
        return null;
    }


    public Container find(String id) {
        if(StringUtils.isEmpty(id)){
            return null;
        }
        try {
            return containerApi.findById(id).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    @Override
    public Boolean deleteChapter(Chapter chapter) {
        try {
            String idUnique = getIdUnique(chapter, project, projectApi);
            projectApi.deleteContainer(project, idUnique).execute();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
