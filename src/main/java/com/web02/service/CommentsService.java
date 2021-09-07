package com.web02.service;

import com.web02.domain.comments.Comments;
import com.web02.domain.comments.CommentsRepository;
import com.web02.domain.posts.Posts;
import com.web02.domain.posts.PostsRepository;
import com.web02.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private  final PostsRepository  postsRepository;
    private final CommentsRepository commentsRepository;


    @Transactional
    public Long saveComments(CommentsRequestDto commentsRequestDto,Long postId){
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + postId));
        Comments comments=commentsRequestDto.toEntity();
        comments.changePost(post);

        return commentsRepository.save(comments).getCommentId();
    }


    @Transactional
    public void deleteComments(Long commentsId){
        Comments comments = commentsRepository.findById(commentsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. commentsId=" + commentsId));

        commentsRepository.delete(comments);
    }

    @Transactional
    public List<CommentsListDto> getCommentList(Long postId){

        return commentsRepository.getCommentsOfPost(postId);
    }


}
