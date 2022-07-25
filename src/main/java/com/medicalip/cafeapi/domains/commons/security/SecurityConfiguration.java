package com.medicalip.cafeapi.domains.commons.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.medicalip.cafeapi.domains.commons.jwt.JwtAuthenticationFilter;
import com.medicalip.cafeapi.domains.commons.jwt.TokenUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final TokenUtils tokenUtils;
	
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("[SecurityConfiguration configure]");
    	
        http
        	.httpBasic().disable()
            // rest api이므로 기본설정 안함. 기본설정은 비인증 시 로그인 폼 화면으로 리다이렉트 된다.
        	.cors()
        	.and()
            .csrf().disable()
            .formLogin().disable()
//                .cors().configurationSource(corsConfigurationSource())
                // rest api 이므로 csrf 보안이 필요 없음. disable
//                .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // jwt token으로 생성하므로 세션은 필요 없으므로 생성 안함.
            .and()
	            .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
	                .antMatchers("/token/refresh", "/test/**", "/user/**", "/swagger-ui/**").permitAll()
	                // 가입 및 인증 주소는 누구나 접근 가능
//	                .antMatchers("/order/**").hasAnyRole("USER")
	                // helloworld로 시작하는 get 요청 리소스는 누구나 접근 가능
	                .anyRequest().authenticated()
          .and()
          .addFilterBefore(new JwtAuthenticationFilter(tokenUtils),
                  UsernamePasswordAuthenticationFilter.class);
	                // 그 외 나머지 요청은 모두 인증된 회원만 접근 가능
        		
    }

    @Override //
    public void configure(WebSecurity web) {
    	System.out.println("[SecurityConfiguration configure]");
        web.ignoring().antMatchers("/v2/api-docs", "/v3/api-docs/**","/swagger-resources/**",
                                    "/swagger-ui.html", "/webjars/**", "/swagger/**","/swagger-ui/**","/api/**", "/user/**", "/**/signin", "/**/signup"
//                                    , "/**/orderfrom"
                                    );
    }
    
    // CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
//        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
