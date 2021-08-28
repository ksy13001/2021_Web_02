package com.web02.domain.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web02.domain.BaseTimeEntity;
import com.web02.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comments_id")
    private Long commentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts postId;


    @Builder
    public Comments(Long commentId, String content,String author,Posts postId){
        this.commentId=commentId;
        this.content=content;
        this.author=author;
        this.postId=postId;
    }

}
