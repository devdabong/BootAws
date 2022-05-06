package com.boot.aws.jj.web;

import com.boot.aws.jj.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 파일 확장자는 자동으로 지정된다.
 * 앞의 경로 : src/main/resources/templates
 * 확장자 : .mustache
 *
 * @RequiredArgsConstructor : 생성자 역할. final이 선언된 모든 필드를 인자값으로 하는 생성자 생성.
 */
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    /**
     * src/main/resources/templates/index.mustache로 전환되어 View Resolver가 처리하게 됨.
     * ViewResolver : URL 요청 결과를 전달할 타입과 값을 지정하는 관리자 격.
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
