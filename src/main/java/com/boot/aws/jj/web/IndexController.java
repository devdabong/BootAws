package com.boot.aws.jj.web;

import com.boot.aws.jj.config.auth.LoginUser;
import com.boot.aws.jj.config.auth.dto.SessionUser;
import com.boot.aws.jj.service.posts.PostsService;
import com.boot.aws.jj.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

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
    private final HttpSession httpSession;

    /**
     * src/main/resources/templates/index.mustache로 전환되어 View Resolver가 처리하게 됨.
     * ViewResolver : URL 요청 결과를 전달할 타입과 값을 지정하는 관리자 격.
     *
     * @LoginUser SessionUser user : 기존에 (SessionUser) httpSession.getAttribute("user")로 가져오던 로직 개선. 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보 GET
     *
     * @return String
     */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        //CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 이전에 구성했었다.
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");    // @LoginUser 어노테이션으로 변경하므로 주석처리.

        if (user != null) {
            System.out.println(">>>>>>> " + user.getName());
            System.out.println(">>>>>>> " + user.getEmail());
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
