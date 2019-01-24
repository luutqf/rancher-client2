package cn.luutqf.rancher.client.bean;

import cn.luutqf.rancher.client.exception.RancherException;
import io.rancher.Rancher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Component
public class RancherBeans {

    @Value("${rancher.host}")
    private String host;

    @Value("${rancher.port}")
    private String port;

    @Value("${rancher.accessKey}")
    private String accessKey;

    @Value("${rancher.secretKey}")
    private String secretKey;

    @Bean
    public Rancher rancher(){
        try {
           return new Rancher(new Rancher.Config(new URL(host+":"+port), accessKey, secretKey));
        } catch (MalformedURLException e) {
            throw new RancherException(e.getMessage(),RancherException.BEAN_ERROR);
        }
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
