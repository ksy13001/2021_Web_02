package com.web02.web.dto;

import com.web02.domain.comments.Comments;
import com.web02.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentsRequestDto {
    private String content;
    private String author;
    private Long  postNo;

    @Builder
    public CommentsRequestDto(String content, String author, Long postNo){
        this.content=content;
        this.author=author;
        this.postNo=postNo;
    }

    public Comments toEntity(){
        return Comments.builder()
                .content(content)
                .author(author)
                .build();
    }
}
