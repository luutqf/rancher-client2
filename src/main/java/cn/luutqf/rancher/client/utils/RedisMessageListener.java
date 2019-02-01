package cn.luutqf.rancher.client.utils;

import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhenYang
 * @date: 2019/1/30
 * @description:
 */
@Component
public class RedisMessageListener implements MessageListener {

    private final ChapterService<Chapter> chapterService;

    @Autowired
    public RedisMessageListener(ChapterService<Chapter> chapterService) {
        this.chapterService = chapterService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        chapterService.delete(message.toString());
    }
}
