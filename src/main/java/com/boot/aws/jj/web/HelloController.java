package com.boot.aws.jj.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController : 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다. 예전 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 처리하는 것.
 * @GetMapping : HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어준다. 예전 @RequestMapping(method=RequestMethod.GET)과 같다.
 */
@RestController
public class HelloController {

    /*
        /hello로 요청이 들어오면 문자열 hello를 반환하는 기능
    */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
