package com.boot.aws.jj.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * 실제로 URL 호출 시 페이지의 내용이 제대로 호출되는 지에 대한 테스트
     *
     * HTML도 결국 규칙 있는 문자열.
     * TestRestTemplate를 통해 "/"를 호출 시 index.mustache에 포함된 코드들이 있는지 확인.
     */
    @Test
    public void 메인페이지_로딩() {

        String body = this.restTemplate.getForObject("/", String.class);

        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }
}
