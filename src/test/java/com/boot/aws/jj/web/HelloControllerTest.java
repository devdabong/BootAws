package com.boot.aws.jj.web;

import com.boot.aws.jj.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.exceptions.base.MockitoSerializationIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/***
 * @RunWith : 테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자(SpringRunner)를 실행시킨다. 즉, 스프링부트 테스트와 JUnit 사이에 연결자 역할
 * @WebMvcTest : Spring MVC에 집중할 수 있는 어노테이션. @Controller와 @ControllerAdvice 등을 사용할 수 있음.
 * @Autowired : 스프링이 관리하는 빈을 주입받음.
 * private MockMvc mvc : 웹 API를 테스트. 스프링 MVC 테스트의 시작점. MockMvc 클래스를 통해 HTTP GET, POST 등 API 테스트를 할 수 있음.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})        // @WebMvcTest는 CustomOAuth2UserService를 스캔하지 않음.
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    /***
     * HTTP GET으로 Controller 보내서 String으로 리턴받는 예제
     * mvc.perform(MockMvcRequestBuilders.get("/hello")) : MockMvc를 통해 /hello 주소로 HTTP GET 요청을 함. 체이닝 지원(여러 검증 기능을 이어서 가능)
     * .andExpect(MockMvcResultMatchers.status().isOk()) : mvc.perform의 결과를 검증함. HTTP Header의 Status를 검증함. 여기선 200인지 아닌지 검증.
     * .andExpect(MockMvcResultMatchers.content().string(hello)) : mvc.perform의 결과를 검증함. 응답 본문의 내용을 검증함. Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증.
     */
    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    /***
     * HTTP GET으로 파라미터 데이터를 Controller 보내는 예제
     * mvc.perform(MockMvcRequestBuilders.get("/hello/dto")) : /hello/dto 주소로 HTTP GET 요청을 함.
     * .param : API 테스트 할 떄 사용될 요청 파라미터 설정. 단, 값은 String만 허용됨.(형변환 필요)
     * jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드. $를 기준으로 필드명을 명시함.
     */
    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
