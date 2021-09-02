package com.web02.service;

import com.web02.domain.posts.Posts;
import com.web02.domain.posts.PostsRepository;
import com.web02.web.dto.PostsResponseDto;
import com.web02.web.dto.PostsUpdateRequestDto;
import com.web02.web.dto.PostsListResponseDto;
import com.web02.web.dto.PostsSaveRequestDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    //생성자 자동생성
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private static final int PAGE_COUNT=4;  // 페이지 개수
    private static final int PAGE_MAX_POSTS=4;  //한페이지 안의 게시글 개수

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional  //delete 는 JPA에서 지원함
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) //= map(posts-> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostsListResponseDto> searchPosts(String keyword){
        List<Posts> posts = postsRepository.findByTitleContaining(keyword);
        List<PostsListResponseDto> postsListResponseDto =new ArrayList<>();


        if(posts.isEmpty()){    //검색결과 없음
            return postsListResponseDto;
        }
        for(Posts post: posts){ //객체 차례대로 삽입
            postsListResponseDto.add(this.toDto(post));
        }
        return postsListResponseDto;
    }

    private PostsListResponseDto toDto(Posts post){
        return PostsListResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .count(post.getCount())
                .build();
    }

    public List<PostsListResponseDto> getPostList(Integer pageNumber){

        Page<Posts> page = postsRepository.findAll(PageRequest.of(pageNumber-1,PAGE_MAX_POSTS, Sort.by(Sort.Direction.ASC,"createdDate")));

        List<Posts> posts=page.getContent();
        List<PostsListResponseDto> postsListResponseDto=new ArrayList<>();

        for(Posts post: posts){
            postsListResponseDto.add(this.toDto(post));
        }
        return postsListResponseDto;
    }

    @Transactional
    public Long getPostCount() {
        return postsRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[PAGE_COUNT];
        Double postsTotalCount = Double.valueOf(this.getPostCount());   //총 게시물 개수
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_MAX_POSTS))); //총 페이지 개수

        Integer blockLastPageNum = (totalLastPageNum > curPageNum + PAGE_COUNT) //마지막 페이지 넘버
                ? curPageNum + PAGE_COUNT
                : totalLastPageNum;

        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;    //처음 시작 페이지

        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }   //페이지 번호 지정

        return pageList;
    }
}