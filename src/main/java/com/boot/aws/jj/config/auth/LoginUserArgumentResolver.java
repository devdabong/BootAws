package com.boot.aws.jj.config.auth;

import com.boot.aws.jj.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

/**
 * HandlerMethodArgumentResolver 인터페이스를 구현한 클래스.
 * @LoginUser 어노테이션을 사용하기 위한 환경 구성 2.
 *
 * HandlerMethodArgumentResolver : 컨트롤러에서 파라미터를 바인딩 해주는 역할.
 *
 * supportsParameter() : 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단함. 바인딩할 클래스를 지정해주는 메서드
 * resolveArgument() : 파라미터에 전달할 객체를 생성함. 바인딩할 객체를 조작할 수 있는 메서드
 */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        // @LoginUser 어노테이션이 붙어 있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 여기서는 세션에서 객체를 가져옴.
        return httpSession.getAttribute("user");
    }
}