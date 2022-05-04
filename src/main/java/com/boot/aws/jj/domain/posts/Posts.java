package com.boot.aws.jj.domain.posts;

import com.boot.aws.jj.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/***
 * 실제 DB 테이블과 매칭될 클래스. (Entity Class)
 * 
 * @Getter, @NoArgsConstructor : lombok의 어노테이션. NoArgsConstructor는 기본 생성자를 자동으로 추가함.
 * @Entity : JPA의 어노테이션. 테이블과 링크될 클래스임을 나타냄. 기본값으로 클래스의 CamelCase 이름을 UnderScore(_) 네이밍으로 테이블명 매칭.
 * @Id : 해당 테이블의 PK 필드를 나타냄.
 * @GeneratedValue : PK 생성 규칙을 나타냄. GenerationType.IDENTITY 옵션을 추가해야 auto_increment가 된다.(springboot2.0 version)
 * @Column : 테이블의 컬럼을 나타냄. 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨.
 *           사용하는 이유는 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용함.
 *           문자열인 경우 VARCHAR(255)가 기본값인데, 사이즈를 변경하거나 타입을 TEXT로 변경하거나 등등에 사용됨.
 * @Builder : 해당 클래스의 빌더 패턴 클래스를 생성. 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함.
 * extends BaseTimeEntity : BaseTimeEneity 추상 클래스를 상속 받음.
 *
 * Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다.
 * 기본구조는 생성자를 통해 최종값을 채운 후 DB에 insert 한다.
 * 값 변경 시 해당 이벤트에 맞는 public 메소드를 호출해 변경한다.
 * 여기서는 생성자 대신 @Builder로 빌더 클래스를 사용한다. 생성자나 빌더나 생성 시점에 값을 채워주는 역할.
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
