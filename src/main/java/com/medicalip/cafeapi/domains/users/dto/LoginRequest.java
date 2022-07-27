package com.medicalip.cafeapi.domains.users.dto;

import java.security.NoSuchAlgorithmException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.medicalip.cafeapi.domains.commons.util.EncryptUtil;

import lombok.Getter;
import lombok.Setter;

public class LoginRequest {
	@Getter
    @Setter
    public static class Login {
        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
        	UsernamePasswordAuthenticationToken authToken = null;
            try {
            	authToken =  new UsernamePasswordAuthenticationToken(email, EncryptUtil.sha512(password));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return authToken;
        }
    }
}
