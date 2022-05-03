package com.boot.aws.jj.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/***
 * assertThat : assertj라는 테스트 검증 라이브러리의 검증 메소드. 검증하고 싶은 대상을 메소드 인자로 받음. 메소드 체이닝 지원됨.
 * isEqualTo : assertj의 동등 비교 메소드. assertThat의 값과 isEqualTo의 값이 같을 때만 성공
 *
 * assertj는 추가적인 라이브러리가 불필요. 자동완성이 좀 더 확실히 지원됨.
 */
public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

}
