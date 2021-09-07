package com.web02.web.dto;
import com.web02.domain.comments.Comments;
import com.web02.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private int views;
    private List<Comments> comments;
    private LocalDateTime modifiedDate;
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.views=entity.getViews();
        this.comments=entity.getComments();
        this.modifiedDate=entity.getModifiedDate();
    }
}
