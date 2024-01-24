package com.gdsc.todo.global.oauth;

import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.global.oauth.userInfo.KakaoOAuth2UserInfo;
import com.gdsc.todo.global.oauth.userInfo.NaverOAuth2UserInfo;
import com.gdsc.todo.global.oauth.userInfo.OAuth2UserInfo;
import com.gdsc.todo.user.dao.SocialType;
import com.gdsc.todo.user.dao.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (socialType == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }

        return ofNaver(userNameAttributeName,attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public User toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .socialType(socialType)
                .socialId(oauth2UserInfo.getOAuth2Id())
                .username(oauth2UserInfo.getNickname())
                .imageUrl(oauth2UserInfo.getProfileImage())
                .role(Role.GUEST)
                .build();
    }
}
