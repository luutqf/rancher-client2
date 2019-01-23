package cn.luutqf.rancher.client;

import cn.luutqf.rancher.client.service.TokenService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author ZhenYang
 * @Date Created in 2018/2/19 23:51
 * @Description
 */
@Component
public class WebSocketClientUtil {


    public static WebSocketClient getWebSocketClient(String uri) {
        WebSocketClient client = null;

        try {
            client = new WebSocketClient(new URI(uri), new Draft_6455()) {
                String key = "";
                Boolean is = false;
                int count = 0;

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("打开链接");
                }

                @Override
                public void onMessage(String s) {
                    Pattern p = Pattern.compile("token=.?\\S+");
                    Pattern p2 = Pattern.compile("http://\\((.*?)\\s+");
                    Matcher m = p2.matcher(s);
                    if (m.find()) {
                        key = m.group(1);
                        is = true;
                    }
                    m = p.matcher(s);
                    if (m.find()) {
                        TokenService.map.put(key, m.group(0));
                        if(null == is){
                            return;
                        }
                        if (is) {
                            is = null;
                        }
                    }
                    if (null == is || count >= 16) {
                        this.close();
                    }
                    count++;
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("链接已关闭");
                }


                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    System.out.println("发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client.connect();
        return client;
    }
}
