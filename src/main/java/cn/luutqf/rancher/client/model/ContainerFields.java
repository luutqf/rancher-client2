package cn.luutqf.rancher.client.model;

import java.util.List;

/**
 * @Author: ZhenYang
 * @date: 2019/1/17
 * @description:
 */
public class ContainerFields {
    private String networkMode;
    private Boolean publishAllPorts;
    private Boolean runInit;
    private String volumeDriver;
    private List<String> dataVolumes;
    private Boolean stdinOpen;
    private Boolean readOnly;
    private List<String> ports;
    private String privileged;
    private String startOnCreate;
    private String tty;
    private String imageUuid;
    private String networkIds;
    private String dns;
    private String dnsSearch;
    private String dataVolumeMounts;
    private String transitioningMessage;
    private String transitioningProgress;
}
