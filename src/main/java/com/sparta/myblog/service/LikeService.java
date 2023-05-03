package com.sparta.myblog.service;

import com.sparta.myblog.dto.ResponseDto;
import com.sparta.myblog.entity.*;
import com.sparta.myblog.exception.CustomException;
import com.sparta.myblog.repository.PostLikesRepository;
import com.sparta.myblog.repository.PostRepository;
import com.sparta.myblog.repository.ReplyLikesRepository;
import com.sparta.myblog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final PostLikesRepository postLikesRepository;
    private final ReplyLikesRepository replyLikesRepository;

    //게시글 좋아요
    public ResponseDto<?> likePost(Long postId, User user) {
        Post post = findPostById(postId);
        Optional<PostLikes> findLike = postLikesRepository.findByPostIdAndUserId(postId, user.getId());
        if (findLike.isPresent()){
            PostLikes postLikes = findLike.get();
            postLikesRepository.delete(postLikes);
            post.addLikeCount(post.getLikeCount()-1);
            return ResponseDto.setSuccess("게시글 좋아요 취소",null);
        } else {
            PostLikes postLikes = new PostLikes(post, user);
            postLikesRepository.save(postLikes);
            post.addLikeCount(post.getLikeCount()+1);
        }
        return ResponseDto.setSuccess("게시글 좋아요 ❤️",null);
    }

    //댓글 좋아요
    public ResponseDto<?> likeReply(Long replyId, User user) {
        Reply reply = findReplyById(replyId);
        Optional<ReplyLikes> findLike = replyLikesRepository.findByReplyIdAndUserId(replyId, user.getId());
        if (findLike.isPresent()){
            ReplyLikes replyLikes = findLike.get();
            replyLikesRepository.delete(replyLikes);
            reply.addLikeCount(reply.getLikeCount()-1);
            return ResponseDto.setSuccess("댓글 좋아요 취소",null);
        } else {
            ReplyLikes replyLikes = new ReplyLikes(reply, user);
            replyLikesRepository.save(replyLikes);
            reply.addLikeCount(reply.getLikeCount()+1);
        }
        return ResponseDto.setSuccess("댓글 좋아요 ❤️",null);
    }

    //게시글 확인
    public Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.POST_NOT_FOUND));
    }
    //댓글 확인
    public Reply findReplyById(Long id){
        return replyRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.REPLY_NOT_FOUND));
    }
}
