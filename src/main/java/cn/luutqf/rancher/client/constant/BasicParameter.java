package cn.luutqf.rancher.client.constant;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Author ZhenYang
 * @Date Created in 2018/1/31 14:32
 * @Description
 */
public class BasicParameter {
    public static final String WebSocketServerURI = "ws://localhost:8081/websocket";
    public static final String FileServer = "http://192.168.20.112:8999/";
    public static final int MaxRequestTime = 10000;
    public static final int MinRequestTime = 10;
    public static final String NfsPrefix = "/nfs/test/";
}
