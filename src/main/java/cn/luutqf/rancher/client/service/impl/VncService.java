package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.VncChapter;
import cn.luutqf.rancher.client.service.ChapterService;
import io.rancher.Rancher;
import io.rancher.base.TypeCollection;
import io.rancher.service.ContainerApi;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import io.rancher.type.ContainerLogs;
import io.rancher.type.InstanceStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import static cn.luutqf.rancher.client.constant.Constants.VNC_PASSWD;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Service
public class VncService implements ChapterService<VncChapter> {

    @Value("${rancher.projectId}")
    private String project;

    private final ProjectApi projectApi;

    @Autowired
    public VncService(Rancher rancher) {
        projectApi = rancher.type(ProjectApi.class);
    }

    public String add(VncChapter vncChapter) {
        try {
            Container container = new Container();
            container.setPorts(Collections.singletonList(vncChapter.getTargetPort()));
            container.setEnvironment(new LinkedHashMap<String, Object>() {{put(VNC_PASSWD, vncChapter.getPassword());}});
            return getId(container, vncChapter, project, projectApi);
        } catch (Exception e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }
}
