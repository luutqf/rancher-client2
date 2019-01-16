package cn.luutqf.rancher.client.test;



import io.rancher.Rancher;
import io.rancher.base.TypeCollection;
import io.rancher.service.ContainerApi;
import io.rancher.service.ProjectApi;
import io.rancher.service.ServiceApi;
import io.rancher.type.Container;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        Rancher.Config config = new Rancher.Config(new URL("http://192.168.20.112:8070"), "7DCF4AB1D5B5AA007CDC", "hpvUwvwgT1E6eaEAPBJYVhXw814JF24XUbfa7omF");
       Rancher rancher = new Rancher(config);

//        ProjectApi projectApi = rancher.type(ProjectApi.class);
//        Container container = new Container();
//        List<String> userPorts = new ArrayList<>();
//        userPorts.add("80");
//        container.setPorts(userPorts);
//        Map<String,Object> envs = new LinkedHashMap<>();
//        envs.put("username","131313");
//        envs.put("password","123456");
//        container.setEnvironment(envs);
//        container.setImageUuid("docker:"+"dorowu/ubuntu-desktop-lxde-vnc");
//        container.setName("luutqf-demo-01");
//        Call<Container> container1 = projectApi.createContainer("1a5", container);
//        Response<Container> execute = container1.execute();
//        assert execute.body() != null;
//        System.out.println(execute.code());
//        System.out.println(execute.errorBody().string());
//        Map<String, Object> data = execute.body().getData();
//        data.forEach((k,v)->{
//            System.out.println(k+" : "+v);
//        });

    }
}
