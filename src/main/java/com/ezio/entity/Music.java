package com.ezio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ezio
 * @date 2017/6/27
 */
@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 音乐id
     */
    private String songId;
    /**
     * 歌名
     */
    private String title;
    /**
     * 歌手
     */
    private String author;
    /**
     * 专辑
     */
    private String album;
    /**
     * 音乐地址
     */
    private String URL;

    /**
     * mp3地址
     */
    private String mp3Url;
    /**
     * 封面
     */
    private String cover;
    /**
     * 评论数
     */
    private int commentCount;

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }
}
