package com.sparta.myblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReplyLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    public ReplyLikes(Reply reply, User user) {
        this.reply = reply;
        this.user = user;
    }
}
