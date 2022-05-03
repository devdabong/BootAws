package com.boot.aws.jj.web;

import com.boot.aws.jj.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController : 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다. 예전 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 처리하는 것.
 * @GetMapping : HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어준다. 예전 @RequestMapping(method=RequestMethod.GET)과 같다.
 * @RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션. ex) 외부에서 name(@RequestParam("name"))이란 이름으로 넘긴 파라미터를 String name에 저장.
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

    /*
        /hello/dto로 요청이 들어오면 HelloResponseDto(name, amount)를 반환하는 기능
    */
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {

        return new HelloResponseDto(name, amount);
    }
}
