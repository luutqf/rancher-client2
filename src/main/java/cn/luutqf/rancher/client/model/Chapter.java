package cn.luutqf.rancher.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

    private String id;

    private String url;

    private String name;

    private String image;

    private String username;
}
