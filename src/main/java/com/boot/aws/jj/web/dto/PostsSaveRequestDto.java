package com.boot.aws.jj.web.dto;

import com.boot.aws.jj.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * Controller, Service에서 사용할 Dto
 *
 * 절대 Entity 클래스를 Request/Response 클래스로 사용하면 안된다. Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스.
 * 꼭 Entity 클래스와 Dto는 구분되어야 한다.
 */
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
