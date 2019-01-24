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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.luutqf.rancher.client.constant.BasicParameter.MaxRequestTime;
import static cn.luutqf.rancher.client.constant.BasicParameter.MinRequestTime;

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

    @Override
    public Optional<String> add(Chapter chapter) {
        return Optional.empty();
    }

    public Object delete(String id) {
        try {
            return projectApi.deleteContainer(project, id).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.DELETE_ERROR);
        }
    }

    public Object start(String id) {
        try {
            return containerApi.start(id).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.START_ERROR);
        }
    }

    public Object stop(String id) {
        try {
            return containerApi.stop(id, new InstanceStop(false, new BigInteger("0"))).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.STOP_ERROR);
        }
    }

    @Override
    public Optional<String> findUrl(String id) {
        Optional<String> url;
        int i = MinRequestTime;
        do{
            url = findUrlUtil(id);
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
            }
        }while ((i+=i)<MaxRequestTime&&!url.isPresent());
        return url;
    }

    private Optional<String> findUrlUtil(String id) {
        Optional<Container> container = find(id);
        Map map = new HashMap();
        List ports = new ArrayList();
        if(container.isPresent()) {
            Map data = container.get().getData();
            if(data.containsKey("fields"))
                map =(Map) data.get("fields");
        }
        if (map.containsKey("ports")) {
            ports = (List) map.get("ports");
        }
        if(!ports.isEmpty()) {
            Pattern p = Pattern.compile(":.?\\d+");
            Matcher m = p.matcher(ports.get(0).toString());
            if (m.find()){
                if (map.containsKey("dockerHostIp"))
                    return Optional.of("http://" + map.get("dockerHostIp") + m.group(0));
            }
        }
        return Optional.empty();
    }

    public Optional<Container> find(String id) {
        if (StringUtils.isEmpty(id)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(containerApi.findById(id).execute().body());
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    @Override
    public Boolean deleteChapter(Chapter chapter) {
        try {
            Optional<String> idUnique = getIdUnique(chapter, project, projectApi);
            if (idUnique.isPresent())
                projectApi.deleteContainer(project, idUnique.get()).execute();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
