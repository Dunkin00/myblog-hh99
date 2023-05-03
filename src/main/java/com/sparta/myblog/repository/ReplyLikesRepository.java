package com.sparta.myblog.repository;

import com.sparta.myblog.entity.PostLikes;
import com.sparta.myblog.entity.ReplyLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikesRepository extends JpaRepository<ReplyLikes, Long> {
    Optional<ReplyLikes> findByReplyIdAndUserId(Long replyId, Long userId);
}
