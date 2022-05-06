package com.boot.aws.jj.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/***
 * MyBatis의 DAO 역할을 하는 DB Layer.
 * JPA에서는 Repository라고 부른다. 인터페이스로 생성.
 * JpaRepository<Entity 클래스, PK 타입> 상속 : 기본적인 CRUD 메소드가 자동 생성.
 *
 * Entity 클래스와 Entity Repository가 같은 위치에 있어야 한다!
 *
 * @Query : SpringDataJpa에서 제공하지 않는 메소드를 쿼리로 작성.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
