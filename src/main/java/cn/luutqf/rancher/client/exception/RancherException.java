package cn.luutqf.rancher.client.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public class RancherException extends RuntimeException{

    public static final Integer BEAN_ERROR = 580;
    public static final Integer CHAPTER_ERROR = 581;

    private Integer code;

    public RancherException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public RancherException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
