package com.web02.web.dto;

import com.web02.domain.comments.Comments;
import com.web02.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
public class PostsListDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;
    private int views;
    private List<Comments> comments;
    private int commentsCnt;
    @Builder
    public PostsListDto(Long id, String title, String author, LocalDateTime modifiedDate, int views, List<Comments> comments ){
        this.id=id;
        this.title=title;
        this.author=author;
        this.modifiedDate=modifiedDate;
        this.views=views;
        this.comments=comments;
        this.commentsCnt=commentsCnt;
    }

    public PostsListDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
        this.views=entity.getViews();
        this.comments=entity.getComments();
        this.commentsCnt=entity.getComments().size();
    }


}
