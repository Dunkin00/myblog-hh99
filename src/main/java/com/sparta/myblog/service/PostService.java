package com.sparta.myblog.service;

import com.sparta.myblog.dto.ResponseDto;
import com.sparta.myblog.dto.PostRequestDto;
import com.sparta.myblog.dto.PostResponseDto;
import com.sparta.myblog.entity.Post;
import com.sparta.myblog.entity.UserRoleEnum;
import com.sparta.myblog.entity.User;
import com.sparta.myblog.exception.CustomException;
import com.sparta.myblog.exception.ErrorCode;
import com.sparta.myblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //게시글 목록 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getList() {
        List<PostResponseDto> postList = postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("게시글 목록 조회", postList);
    }

    //선택한 게시글 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id){
        Post post = findPostById(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return ResponseDto.setSuccess("선택한 게시글 조회", postResponseDto);
    }

    //게시글 등록
    public ResponseDto<?> createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user);
        PostResponseDto postResponseDto = new PostResponseDto(postRepository.save(post));
        return ResponseDto.setSuccess("게시글 등록 성공", postResponseDto);
    }

    //게시글 수정
    public ResponseDto<?> update(Long id, PostRequestDto requestDto, User user) {
        Post post = findPostById(id);
        if (user.getRole().equals(UserRoleEnum.USER)) {
            isUsersPost(user,post);
        }
        post.update(requestDto);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return ResponseDto.setSuccess("게시글 수정 성공", postResponseDto);
    }

    //게시글 삭제
    public ResponseDto<?> deletePost(Long id, User user) {
        Post post = findPostById(id);
        if (user.getRole().equals(UserRoleEnum.USER)) {
            isUsersPost(user,post);
        }
        postRepository.deleteById(id);
        return ResponseDto.setSuccess("게시글 삭제 완료", null);
    }

    //게시글 확인
    public Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    //작성자 게시물 확인
    public void isUsersPost(User user, Post post){
        if (!post.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NOT_AUTHORIZED_USER);
        }
    }
}