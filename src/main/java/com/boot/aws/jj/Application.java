package com.boot.aws.jj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 메인클래스
 * 
 * @SpringBootApplication : 스프링부트 자동설정, 스프링 Bean 읽기와 생성을 모두 자동 설정. 항상 프로젝트의 최상단에 위치(여기부터 설정을 읽어가기 때문)
 * */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);     // 내장 WAS(Web Application Server)를 실행.
    }
}
