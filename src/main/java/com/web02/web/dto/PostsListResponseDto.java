package com.web02.web.dto;

import com.web02.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;
    private Long count;

    @Builder
    public PostsListResponseDto(Long id, String title, String author,LocalDateTime modifiedDate, Long count ){
        this.id=id;
        this.title=title;
        this.author=author;
        this.modifiedDate=modifiedDate;
        this.count=count;
    }

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
        this.count=entity.getCount();
    }


}
