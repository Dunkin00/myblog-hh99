package com.sparta.myblog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.myblog.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;
    @Column(nullable = false)
    private String contents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    public Reply(ReplyRequestDto requestDto, User user, Post post) {
        this.contents = requestDto.getContents();
        this.user = user;
        this.post = post;
    }

    public void update(ReplyRequestDto requestDto, User user){
        this.contents = requestDto.getContents();
        this.user = user;
    }
}
