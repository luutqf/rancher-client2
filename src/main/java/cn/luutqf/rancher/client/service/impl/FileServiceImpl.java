package cn.luutqf.rancher.client.service.impl;

import cn.luutqf.rancher.client.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static cn.luutqf.rancher.client.constant.BasicParameter.FileServer;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description: 在没有微服务架构的情况下，需要指定服务
 */
@Service
public class FileServiceImpl implements FileService {

    private final RestTemplate restTemplate;

    @Autowired
    public FileServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Boolean create(String path) {
        return restTemplate.getForObject(FileServer+"create?path="+path, Boolean.class);
    }

    @Override
    public Boolean move(String oldPath, String newPath) {
        return restTemplate.getForObject(FileServer+"move?oldPath="+oldPath+"&newPath="+newPath, Boolean.class);
    }

    @Override
    public Boolean copy(String oldPath, String newPath) {
        return restTemplate.getForObject(FileServer+"copy?oldPath="+oldPath+"&newPath="+newPath, Boolean.class);
    }

    @Override
    public Boolean delete(String path) {
        return restTemplate.getForObject(FileServer+"delete?path="+path, Boolean.class);
    }

    @Override
    public Boolean chmod(String path) {
        return restTemplate.getForObject(FileServer+"chmod?path="+path,Boolean.class);
    }
}
