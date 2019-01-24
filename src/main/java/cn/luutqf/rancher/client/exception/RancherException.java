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
    public static final Integer STOP_ERROR = 585;
    public static final Integer START_ERROR = 586;
    public static final Integer THREAD_ERROR = 588;
    public static final Integer DELETE_ERROR = 587;
    public static final Integer JUPYTER_EMPTY = 582;
    public static final Integer WEB_SOCKET_ERROR = 589;
    public static final Integer CREATE_CHAPTER_ERROR = 583;
    public static final Integer LIST_CONTAINER_ERROR = 584;

    private Integer code;

    public RancherException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public RancherException(String message) {
        super(message);
    }
    public RancherException(Integer code) {
        super("error code : "+code);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
