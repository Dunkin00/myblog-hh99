package com.sparta.myblog.entity;

import com.sparta.myblog.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @OrderBy("createdAt desc")
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLikes> postLikes = new ArrayList<>();

    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void addLikeCount(int likeCount){
        this.likeCount = likeCount;
    }
}