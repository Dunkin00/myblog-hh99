package com.sparta.myblog.controller;

import com.sparta.myblog.dto.ResponseDto;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    //게시글 좋아요
    @PostMapping("/posts/{post-id}/like")
    public ResponseDto<?> likePost (@PathVariable(name = "post-id")Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.likePost(postId, userDetails.getUser());
    }

    //댓글 좋아요
    @PostMapping("/reply/{reply-id}/like")
    public ResponseDto<?> likeReply (@PathVariable(name = "reply-id")Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.likeReply(postId, userDetails.getUser());
    }
}
