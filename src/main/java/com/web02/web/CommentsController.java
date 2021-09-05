package com.web02.web;


import com.web02.service.CommentsService;
import com.web02.service.PostsService;
import com.web02.web.dto.CommentsRequestDto;
import com.web02.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentsController {

    private final PostsService postService;
    private final CommentsService commentsService;


    @PostMapping("comments/{postId}")
    public Long saveComments(@PathVariable Long postId, @RequestBody CommentsRequestDto commentsRequestDto){
        commentsRequestDto.setPostNo(postId);
        return commentsService.saveComments(commentsRequestDto,postId);
    }



    @DeleteMapping("/comments/{postId}/{id}")
    public Long deleteComments(@PathVariable Long postId,@PathVariable Long id){
        commentsService.deleteComments(id);
        return id;
    }

}

