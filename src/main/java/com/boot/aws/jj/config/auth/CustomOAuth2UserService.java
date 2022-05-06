package com.boot.aws.jj.config.auth;

import com.boot.aws.jj.config.auth.dto.OAuthAttributes;
import com.boot.aws.jj.config.auth.dto.SessionUser;
import com.boot.aws.jj.domain.user.User;
import com.boot.aws.jj.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 구글 로그인 이후 사용자 정보들을 기반으로 가입 및 정보수정, 세션 저장 등 기능 지원.
 *
 * OAuth2UserRequest.getClientRegistration().getRegistrationId() : 현재 로그인 진행 중인 서비스를 구분하는 코드. 구글만 있을 땐 불필요. 다른 소셜 로그인 있을 때 구분자로 사용.
 * .getUserNameAttributeName() : OAuth2 로그인 진행 시 키가 되는 필드값. Primary Key와 같은 의미. 구글은 기본 코드는 "sub". 이후 네이버 로그인과 구글 로그인 동시 지원 시 사용됨.
 * OAUthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스.
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        // User 클래스 대신 SessionUser를 구현한 이유 : User 클래스는 직렬화를 구현하지 않음. 직렬화 기능을 가진 DTO를 추가로 만드는 것이 유지보수에 유리.
        // 만약 User 클래스(엔티티)에 직렬화를 구현하면 @OneToMany, @ManyToMany 등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함됨.
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
