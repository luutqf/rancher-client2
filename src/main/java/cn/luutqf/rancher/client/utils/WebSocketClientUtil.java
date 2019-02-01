package cn.luutqf.rancher.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Function;

/**
 * @Author ZhenYang
 * @Date Created in 2018/2/19 23:51
 * @Description
 */
@Component
@Slf4j
public class WebSocketClientUtil {


    //todo 函数式编程
    public static Optional<WebSocketClient> getWebSocketClient(String uri, String key) {
        WebSocketClient client;


        try {
            client = new WebSocketClient(new URI(uri), new Draft_6455()) {
                int count = 0;

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("打开链接:{}", serverHandshake.getHttpStatus());
                }

                @Override
                public void onMessage(String s) {
                    //todo 这里不该这样
                    if (++count >= 16) {
                        this.close();
                    }
                    LogsUtil.logMap2.get(key).append(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    log.info("链接已关闭:{}", s);
                }


                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    log.error("发生错误已关闭:{}", e.getMessage());
                }
            };
            client.connect();
            return Optional.of(client);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("初始化发生错误:{}", e.getMessage());
        }

        return Optional.empty();
    }
}
