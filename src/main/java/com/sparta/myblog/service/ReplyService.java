package com.sparta.myblog.service;

import com.sparta.myblog.dto.ResponseDto;
import com.sparta.myblog.dto.ReplyResponseDto;
import com.sparta.myblog.dto.ReplyRequestDto;
import com.sparta.myblog.entity.*;
import com.sparta.myblog.exception.CustomException;
import com.sparta.myblog.repository.PostRepository;
import com.sparta.myblog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    //댓글 작성
    public ResponseDto<?> createReply(Long postId, ReplyRequestDto requestDto, User user) {
        Post post = findPostById(postId);
        Reply reply = new Reply(requestDto, user, post);
        replyRepository.save(reply);
        ReplyResponseDto replyResponseDto = new ReplyResponseDto(reply);
        return ResponseDto.setSuccess("댓글 작성 완료", replyResponseDto);
    }

    //댓글 수정
    public ResponseDto<?> updateReply(Long replyId, ReplyRequestDto requestDto, User user){
        Reply reply = findReplyById(replyId);
        if (user.getRole().equals(UserRoleEnum.USER)){
            isUserReply(user,reply);
        }
        reply.update(requestDto, user);
        ReplyResponseDto replyResponseDto = new ReplyResponseDto(reply);
        return ResponseDto.setSuccess("댓글 수정 완료", replyResponseDto);
    }

    //댓글 삭제
    public ResponseDto<?> deleteReply(Long replyId, User user){
        Reply reply = findReplyById(replyId);
        if (user.getRole().equals(UserRoleEnum.USER)){
            isUserReply(user,reply);
        }
        replyRepository.delete(reply);
        return ResponseDto.setSuccess("댓글 삭제 완료", null);
    }

    //게시글 확인
    public Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.POST_NOT_FOUND));
    }
    //댓글 확인
    public Reply findReplyById(Long id){
        return replyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }
    //댓글 주인 확인
    public void isUserReply(User user, Reply reply){
        if (!reply.getUser().getId().equals(user.getId())) {
            throw new CustomException(StatusEnum.NOT_AUTHORIZED_USER);
        }
    }
}
