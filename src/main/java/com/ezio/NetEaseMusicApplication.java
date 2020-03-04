package com.ezio;

import com.ezio.pipeline.NetEaseMusicPipeline;
import com.ezio.processor.NetEaseMusicPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class NetEaseMusicApplication {

    @Autowired
    NetEaseMusicPageProcessor mProcessor;
    @Autowired
    NetEaseMusicPipeline mPipeline;

    /**
     * 开始爬虫
     *
     * @param playListId 网易云歌单id
     * @return
     */
    @GetMapping("/{playListId}")
    public String index(@PathVariable String playListId) {
        new Thread(() -> mProcessor.start(mProcessor, mPipeline, playListId)).start();

        return "爬虫开启";
    }

    public static void main(String[] args) {
        SpringApplication.run(NetEaseMusicApplication.class, args);

    }
}
