package com.medicalip.cafe.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // jpa entity 임을 알린다.
@Data //user 필드 값의 getter를 자동생성한다.
@NoArgsConstructor // 인자 없는 생성자를 자동으로 생성한다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성한다.
@Builder // builder를 사용할 수 있게 한다.
public class User {
	
	@Id //primaryKey 임을 알린다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // primaryKey 생성 전략을 DB에 위임한다는 뜻.
    // primaryKey 필드를 auto_increment로 설정해 놓은 경우와 같다.
    private long userSeq;
	
    // email을 명시한다. 필수 입력, 중복 안됨, 길이는 100제한
    private String email;
    private String password;
    private String name; // name을 명시, 필수 입력, 길이 100 제한
//    private String roles; // 회원이 가지고 있는 권한
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<UserRole> role = new ArrayList<>();
    private LocalDateTime edtTime; // 
    private LocalDateTime regTime; // 

}
