package com.medicalip.cafeapi.domains.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequest {
	private String email;
    private String password;
    private String name;
    private String role;
}
