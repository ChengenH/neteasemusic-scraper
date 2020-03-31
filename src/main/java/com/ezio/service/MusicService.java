package com.ezio.service;

import com.ezio.entity.MusicComment;
import com.ezio.entity.Music;
import com.ezio.repository.CommentRepository;
import com.ezio.repository.MusicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ezio on 2017/6/28.
 */
@Service
public class MusicService {
	@Autowired
	private MusicRepository mMusicRepository;
	@Autowired
	private CommentRepository mCommentRepository;

	public void addMusic(Music music) {
		//判断数据是否存在
		if (mMusicRepository.countBySongId(music.getSongId()) == 0) {
			mMusicRepository.save(music);
		}

	}

	public void addComment(MusicComment musicComment) {
		//判断数据是否存在
		if (mCommentRepository.countByCommentId(musicComment.getCommentId()) == 0) {
			mCommentRepository.save(musicComment);
		}
	}



	public void addComments(List<MusicComment> musicComments) {
		mCommentRepository.save(musicComments);
	}
}
