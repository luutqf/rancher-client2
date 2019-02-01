package cn.luutqf.rancher.client.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @Author: ZhenYang
 * @date: 2019/1/30
 * @description:
 */
@Configuration
public class PubsubConfiguration  {

    private final RedisMessageListener redisMessageListener;

    @Autowired
    public PubsubConfiguration(RedisMessageListener redisMessageListener) {
        this.redisMessageListener = redisMessageListener;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            @Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(redisMessageListener, new ChannelTopic("__keyevent@0__:expired"));
        return redisMessageListenerContainer;
    }


}
