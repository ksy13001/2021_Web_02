package com.web02.web;

import com.web02.config.auth.LoginUser;
import com.web02.config.auth.dto.SessionUser;
import com.web02.domain.posts.Posts;
import com.web02.service.CommentsService;
import com.web02.service.PostsService;
import com.web02.web.dto.CommentsListDto;
import com.web02.web.dto.PostsListDto;
import com.web02.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;    //model 을 사용하여 View에 데이터 전달
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final CommentsService commentsService;
    private final HttpSession httpSession;

    @GetMapping("/")    //   첫화면 index+ 글 조회
    public String index(Model model,@LoginUser SessionUser user, @PageableDefault(size=3,sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        //model: postService.findAllDesc()로 가져온 결과 posts로 index.mustache에 전달
        Page<Posts> postsList=postsService.getPostList(pageable);
        model.addAttribute("postList", postsList);
        model.addAttribute("posts", postsService.findAllDesc());


        if(user!=null){ //유저값이 없을때만
            model.addAttribute("userName",user.getName());
        }
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("/posts/save")  //글 등록화면 이동
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")    //글 수정화면 이동,id로 구분
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts",dto);

        return "posts-update";
    }

    //댓글
    @GetMapping("/posts/{id}")
    public String postsView(@PathVariable Long id, Model model){
        PostsResponseDto postsResponseDto = postsService.findById(id);
        List<CommentsListDto> commentsListDtos=commentsService.getCommentList(id);
        model.addAttribute("posts",postsResponseDto);
        model.addAttribute("comments",commentsListDtos);
        model.addAttribute("view", postsService.updateView(id));
        return "posts-view";
    }

    @GetMapping("/posts/search")
    public String Search(Model model, @RequestParam(value="keyword") String keyword){
        List<Posts> searchList = postsService.search(keyword);
        model.addAttribute("searchList", searchList);   //List<Posts> postList
        return "posts-search";
    }
}
