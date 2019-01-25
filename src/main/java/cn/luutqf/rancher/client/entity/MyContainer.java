package cn.luutqf.rancher.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    protected List<String> ports;

    protected Map<String,Object> labels;

    protected List<String> dataVolumes;

    protected String volumeDriver;

    protected String imageUuid;

    protected String name;

    protected Map<String,Object> environment;


}
