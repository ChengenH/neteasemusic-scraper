package com.ezio.repository;

import com.ezio.entity.MusicComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ezio on 2017/6/28.
 */

public interface CommentRepository extends JpaRepository<MusicComment, Integer> {
    int countByCommentId(Long commentId);
}
