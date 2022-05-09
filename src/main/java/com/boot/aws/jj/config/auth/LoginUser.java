package com.boot.aws.jj.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LoginUser 어노테이션 클래스
 * @LoginUser 어노테이션을 사용하기 위한 환경 구성 1.
 *
 * @Target(ElementType.PARAMETER) : 이 어노테이션이 생성될 수 있는 위치를 지정. PARAMETER로 지정했으므로 메소드의 파라미터로 선언된 객체에서만 사용가능.
 * @interface : 이 파일을 어노테이션 클래스로 지정.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
