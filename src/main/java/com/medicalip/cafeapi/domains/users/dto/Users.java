package com.medicalip.cafeapi.domains.users.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.medicalip.cafeapi.domains.commons.util.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성한다.
@Builder
@Entity
@Setter
@Getter
@Table(name = "users")
public class Users implements UserDetails{
	@Id //primaryKey 임을 알린다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // primaryKey 생성 전략을 DB에 위임한다는 뜻.
    // primaryKey 필드를 auto_increment로 설정해 놓은 경우와 같다.
    private long id;
	
    // email을 명시한다. 필수 입력, 중복 안됨, 길이는 100제한
	@Column
    private String email;
	
	@Column
    private String password;
	
	@Column
    private String name; // name을 명시, 필수 입력, 길이 100 제한
//    private String roles; // 회원이 가지고 있는 권한
	
//    @JoinTable(name = "user_role",
//    		joinColumns = @JoinColumn(name = "")
//    )
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
//    private Collection<UserRole> roles = new ArrayList<>();
    
    @Column
    private LocalDateTime edtTime; //
    
    @Column
    private LocalDateTime regTime; //
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
