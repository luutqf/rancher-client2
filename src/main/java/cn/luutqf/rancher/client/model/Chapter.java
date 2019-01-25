package cn.luutqf.rancher.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static cn.luutqf.rancher.client.constant.Constants.CONTAINER_TYPE;

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

    {
        this.bodyType = CONTAINER_TYPE;
        this.containerType = "docker:";
    }
    protected String bodyType;

    protected String containerType;

    private String id;

    private String url;

    private String name;
    @NotNull(message = "镜像不能为空")
    private String image;
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "章节不能为空")
    private String chapterName;

    @NotNull(message = "端口不能为空")
    private String targetPort;

    @NotNull(message = "科目不能为空")
    private String subjectName;

    @NotNull(message = "过期时间不能为空")
    private Integer ttl;
}
