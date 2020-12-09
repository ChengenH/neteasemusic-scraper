package com.ezio.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.ezio.entity.Music;
import com.ezio.entity.MusicComment;
import com.ezio.entity.MusicCommentDto;
import com.ezio.pipeline.NetEaseMusicPipeline;
import com.ezio.service.MusicService;
import com.ezio.utils.HttpUtils;
import com.ezio.utils.NetEaseMusicUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by Ezio on 2017/6/27.
 */
@Component
public class NetEaseMusicPageProcessor implements PageProcessor {
    // 正则表达式\\. \\转义java中的\ \.转义正则中的.
    /**
     * 主域名
     */
    public static final String BASE_URL = "http://music.163.com/";
    /**
     * 匹配专辑URL
     */
    public static final String ALBUM_URL = "http://music\\.163\\.com/playlist\\?id=\\d+";
    /**
     * 匹配歌曲URL
     */
    public static final String MUSIC_URL = "http://music\\.163\\.com/song\\?id=\\d+";
    /**
     * 初始地址, 帅侯飞喜欢的音乐id 148174530
     */
    public static final String START_URL = "http://music.163.com/playlist?id=104318488";
    public static final int ONE_PAGE = 200;
    private int timestamp = (int) (System.currentTimeMillis() / 1000);
    private final String authHeader = authHeader("ZF20179221632tODs6v", "038de086e3b34575a4af7be000f41f89", timestamp);
    private Site site = Site.me()
            .setDomain("http://music.163.com")
            .setSleepTime(1000)
            .setRetryTimes(30)
            .setCharset("utf-8")
            .setTimeOut(30000)
            .addHeader("Proxy-Authorization", authHeader)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public Site getSite() {
        return site;
    }

    @Autowired
    MusicService mMusicService;

    @Override
    public void process(Page page) {
        // 根据URL判断页面类型
        if (page.getUrl().regex(ALBUM_URL).match()) {
            System.out.println("歌曲总数----->" + page.getHtml().xpath("//span[@id='playlist-track-count']/text()").toString());
            // 爬取歌曲URl加入队列
            page.addTargetRequests(page.getHtml().xpath("//div[@id=\"song-list-pre-cache\"]").links().regex(MUSIC_URL).all());
        } else {
            String url = page.getUrl().toString();
            Music music = new Music();
            // 单独对AJAX请求获取评论数, 使用JSON解析返回结果
            String songId = url.substring(url.indexOf("id=") + 3);
            int commentCount = getComment(page, songId, 0);
            // music 保存到数据库
            music.setSongId(songId);
            music.setCommentCount(commentCount);
            music.setTitle(page.getHtml().xpath("//em[@class='f-ff2']/text()").toString());
            music.setAuthor(page.getHtml().xpath("//p[@class='des s-fc4']/span/a/text()").toString());
            music.setAlbum(page.getHtml().xpath("//p[@class='des s-fc4']/a/text()").toString());
            music.setURL(url.replace("http","https"));
            music.setMp3url("https://music.163.com/song/media/outer/url?id=" + songId + ".mp3");
            JSONObject jsonObject = JSONObject.parseObject(HttpUtils.httpGetRequest("https://music.163.com/api/song/media?id=" + songId));
            if(jsonObject.get("lyric")==null){
                music.setLyric("");
            }else {
                music.setLyric(jsonObject.get("lyric").toString());
            }

            String substring = page.getRawText().substring(0, page.getRawText().indexOf("\" />\n" +
                    "<meta property=\"og:url\""));
            substring = substring.substring(substring.indexOf("<meta property=\"og:image\" content=\""));
            //封面
            String cover = null;
            try {
                cover = substring.substring(substring.indexOf("http://p1.music.126.net"));
            } catch (Exception e) {
                cover = substring.substring(substring.indexOf("http://p2.music.126.net"));
            }
            music.setCover(cover.replace("http", "https"));
            mMusicService.addMusic(music);
        }
    }

    private int getComment(Page page, String songId, int offset) {
        int commentCount;
        String s = NetEaseMusicUtils.crawlAjaxUrl(songId, offset);

        if (s.contains("503 Service Temporarily Unavailable")) {
            commentCount = -1;
        } else {
            JSONObject jsonObject = JSON.parseObject(s);
            List<MusicCommentDto> hotComments = JSON.parseArray(jsonObject.get("hotComments").toString(), MusicCommentDto.class);
            commentCount = (Integer) JSONPath.eval(jsonObject, "$.total");
            hotComments.forEach(i -> {
                // 保存到数据库
                MusicComment musicComment = new MusicComment();
                musicComment.setCommentId(i.getCommentId());
                musicComment.setSongId(songId);
                musicComment.setContent(NetEaseMusicUtils.filterEmoji(i.getContent()).replace("\"", ""));
                musicComment.setLikedCount(i.getLikedCount());
                musicComment.setNickname(i.getUser().getNickname());
                musicComment.setTime(NetEaseMusicUtils.stampToDate(i.getTime()));
                mMusicService.addComment(musicComment);
            });
        }

        return commentCount;
    }


    /**
     * 开始爬虫
     *
     * @param processor
     * @param netEaseMusicPipeline
     * @param playlistId           歌单id
     */
    public void start(NetEaseMusicPageProcessor processor, NetEaseMusicPipeline netEaseMusicPipeline, String playlistId) {
        long start = System.currentTimeMillis();
        //初始歌单id
        if (StringUtils.isBlank(playlistId)) {
            playlistId = "104318488";
        }
        Spider.create(processor)
                .addUrl("http://music.163.com/playlist?id=" + playlistId)
                .thread(5)
                .run();
        long end = System.currentTimeMillis();
        System.out.println("爬虫结束,耗时--->" + NetEaseMusicUtils.parseMillisecone(end - start));

    }


    /**
     * http://www.xdaili.cn/usercenter/order
     * 讯代理 买了10W 的
     *
     * @param orderno
     * @param secret
     * @param timestamp
     * @return
     */

    public static String authHeader(String orderno, String secret, int timestamp) {
        //拼装签名字符串
        String planText = String.format("orderno=%s,secret=%s,timestamp=%d", orderno, secret, timestamp);

        //计算签名
        String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(planText).toUpperCase();

        //拼装请求头Proxy-Authorization的值
        String authHeader = String.format("sign=%s&orderno=%s&timestamp=%d", sign, orderno, timestamp);
        return authHeader;
    }

}
