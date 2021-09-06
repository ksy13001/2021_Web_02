package com.web02.domain.comments;


import com.web02.domain.posts.Posts;
import com.web02.web.dto.CommentsListDto;
import com.web02.web.dto.CommentsRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends CrudRepository<Comments, Long> {
    @Query("SELECT c from Comments c where c.post.id=:postId and c.id>0 order by c.id ASC ")
    public List<CommentsListDto> getCommentsOfPost(@Param("postId") Long postId);
}
