package cn.luutqf.rancher.client.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhenYang
 * @date: 2019/1/8
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    public static final Integer OK = 200;
    public static final Integer ERROR = 500;
    public static final Integer DB_ERROR = 501;
    public static final Integer HAINA_ERROR = 502;

    public static final String success = "success";
    public static final String failed = "failed";

    private Integer code;
    private Object data;
    private String msg;


    public static Result build(Integer status,Object data, String msg) {
        return new Result(status,data,msg);
    }
    public static Result build(Integer status,Object data) {
        return new Result(status,data,"");
    }
    public static Result build(Object data){
        return new Result(Result.OK,data,Result.success);
    }
}
