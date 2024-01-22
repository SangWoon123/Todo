package com.gdsc.todo.user.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {

    GOOGLE("GOOGLE"),
    KAKAO("KAKAO");

    private final String socialName;

    public static SocialType getSocialType(String registrationId) {
        if(KAKAO.socialName.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }
}
