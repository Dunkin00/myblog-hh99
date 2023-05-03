package com.sparta.myblog.repository;

import com.sparta.myblog.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    Optional<PostLikes> findByPostIdAndUserId(Long postId, Long userId);
}
