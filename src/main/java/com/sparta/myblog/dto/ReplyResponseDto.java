package com.sparta.myblog.dto;

import com.sparta.myblog.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReplyResponseDto {
    private Long id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likeCount;

    public ReplyResponseDto(Reply reply){
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.username = reply.getUser().getUsername();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
        this.likeCount = reply.getLikeCount();
    }
}
