package cn.luutqf.rancher.client.utils;

import cn.luutqf.rancher.client.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author ZhenYang
 * @Date Created in 2018/2/19 23:51
 * @Description
 */
@Component
@Slf4j
public class WebSocketClientUtil {


    public static Optional<WebSocketClient> getWebSocketClient(String uri) {
        WebSocketClient client ;

        try {
            client = new WebSocketClient(new URI(uri), new Draft_6455()) {
                String key = "";
                int isToken = 0;
                int count = 0;

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("打开链接:{}", serverHandshake.getHttpStatus());
                }

                @Override
                public void onMessage(String s) {
                    Pattern p = Pattern.compile("token=.?\\S+");
                    Pattern p2 = Pattern.compile("http://\\((.*?)\\s+");
                    Matcher m = p2.matcher(s);
                    if (m.find()) {
                        key = m.group(1);
                        isToken = 1;
                    }
                    m = p.matcher(s);
                    if (m.find()) {
                        TokenService.map.put(key, m.group(0));
                        if (isToken == 2) {
                            return;
                        }
                        if (isToken == 1) {
                            isToken = 2;
                        }
                    }
                    if (isToken == 2 || count >= 16) {
                        this.close();
                    }
                    count++;
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
