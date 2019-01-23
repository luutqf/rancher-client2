package cn.luutqf.rancher.client.model;

import lombok.*;

import java.util.List;

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
        this.bodyType = "jupyter-";
        this.containerType = "docker:";
    }


    private List<String> files;

    private String file;



}
