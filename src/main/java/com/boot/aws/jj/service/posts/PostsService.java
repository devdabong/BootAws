package com.boot.aws.jj.service.posts;

import com.boot.aws.jj.domain.posts.Posts;
import com.boot.aws.jj.domain.posts.PostsRepository;
import com.boot.aws.jj.web.dto.PostsResponseDto;
import com.boot.aws.jj.web.dto.PostsSaveRequestDto;
import com.boot.aws.jj.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 * Spring 에서는 Controller와 Service에서 @Autowired, setter, 생성자로 Bean을 주입받는다.
 *
 * @RequiredArgsConstructor : 생성자 역할. final이 선언된 모든 필드를 인자값으로 하는 생성자 생성.
 */
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    /**
     * 게시글 등록
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * 게시글 update
     * @param id
     * @param requestDto
     * @return
     *
     * DB에 쿼리를 날리는 부분이 없다! => JPA의 영속성 때문에 필요없음.
     * 영속성 컨텍스트 : 엔티티를 영구저장하는 환경.
     * 더티체킹 : JPA의 엔티티 매니저가 활성화된 상태(Spring Data Jpa의 기본옵션)로 트랜잭션 안에서 DB에서 데이터를 가져오면, 이 데이터는 영속성 컨텍스트가 유지된 상태.
     *          해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에서 해당 테이블에 반영.
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /**
     * id로 게시글 get
     * @param id
     * @return
     */
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }
}
