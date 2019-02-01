package cn.luutqf.rancher.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyContainer {

    /**
     * rancher 或者 k8s
     */
    private String platform;

    private String uiPort;

    //修改为servicePorts
    protected List<String> ports;

    protected Map<String,Object> labels;

    protected List<String> dataVolumes;

    //rancher的，可以考虑继承
    protected String volumeDriver;

    //todo 修改为image
    protected String imageUuid;

    //容器名
    protected String name;

    protected Map<String,Object> environment;

    private Integer ttl;

    private BigInteger cpuShares;

    private BigInteger memory;

    private BigInteger memoryReservation;

    private String servicePassword;

}
