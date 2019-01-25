package cn.luutqf.rancher.client.model;

import lombok.*;

import java.util.List;

import static cn.luutqf.rancher.client.constant.Constants.JUPYTER_TYPE;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JupyterChapter extends Chapter {

    {
        this.bodyType = JUPYTER_TYPE;
        this.containerType = "docker:";
    }


    private List<String> files;

    private String file;



}
