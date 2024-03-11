package com.gdsc.todo.global.config;

import com.gdsc.todo.global.config.redis.RedisOAuth2AuthorizedClientService;
import com.gdsc.todo.global.oauth.CustomOAuth2Service;
import com.gdsc.todo.global.oauth.handler.OAuth2LoginFailureHandler;
import com.gdsc.todo.global.oauth.handler.Oauth2SuccessHandler;
import com.gdsc.todo.global.oauth.handler.logout.CustomLogoutHandler;
import com.gdsc.todo.global.oauth.handler.logout.CustomLogoutSuccessHandler;
import com.gdsc.todo.global.token.JwtAuthenticationFilter;
import com.gdsc.todo.global.token.JwtExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final CustomOAuth2Service customOAuth2Service;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;
    // 로그아웃
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomLogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(
                                "/", "/swagger-ui/**", "/**",
                                "**.html", "**.css", "**.js",
                                "/swagger-resources/**", "/webjars/**", "/v3/api-docs/**"
                        )
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2Service))
                        .successHandler(oauth2SuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .authorizedClientService(authorizedClientService()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                );

        return http.build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new RedisOAuth2AuthorizedClientService(clientRegistrationRepository);
    }
}
