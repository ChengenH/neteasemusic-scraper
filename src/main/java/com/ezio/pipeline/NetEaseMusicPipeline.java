package com.ezio.pipeline;

import com.ezio.entity.Music;
import com.ezio.entity.MusicComment;
import com.ezio.repository.CommentRepository;
import com.ezio.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by Ezio on 2017/6/28.
 */
@Component
public class NetEaseMusicPipeline implements Pipeline {

    @Autowired
    public MusicRepository mMusicDao;

    @Autowired
    public CommentRepository mCommentDao;

    @Override
    public void process(ResultItems resultItems, Task task) {

        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().equals("music")) {
                Music music = (Music) entry.getValue();
                System.out.println("mMusicDao--->null" + mMusicDao == null);
                if (mMusicDao.countBySongId(music.getSongId()) == 0) {
                    mMusicDao.save(music);
                }
            } else {
                MusicComment musicComment = (MusicComment) entry.getValue();
                System.out.println("mCommentDao--->null" + mCommentDao == null);
                if (mCommentDao.countByCommentId(musicComment.getCommentId()) == 0) {
                    mCommentDao.save(musicComment);
                }
            }

        }
    }


}
