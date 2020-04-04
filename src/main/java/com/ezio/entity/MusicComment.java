package com.ezio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ezio
 * @date 2017/6/28
 */
@Entity
@Table(name = "music_comment")
public class MusicComment {
	
	@Id
	@GeneratedValue
	private Integer	id;

	private String	songId;

	/**
	 * 评论昵称
	 */
	private String	nickname;
	/**
	 * 喜欢数
	 */
	private Integer	likedCount;
	/**
	 * 评论内容
	 */
	private String	content;
	/**
	 * 评论时间
	 */
	private String time;
	/**
	 * 评论id
	 */
	private Long commentId;


	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSongId() {
		return songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
