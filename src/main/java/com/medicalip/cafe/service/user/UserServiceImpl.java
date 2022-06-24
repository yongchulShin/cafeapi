package com.medicalip.cafe.service.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medicalip.cafe.advice.exception.CUserNotFoundException;
import com.medicalip.cafe.entity.User;
import com.medicalip.cafe.entity.UserRole;
import com.medicalip.cafe.repository.UserJpaRepository;
import com.medicalip.cafe.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService{
	private final UserJpaRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    
//    @Override
//    public UserDetails loadUserByEmail(String email) throws CUserNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if(user == null) {
//            log.error("User not found in the database {}", email);
//            throw new CUserNotFoundException("User not found in the database");
//        } else {
//            log.info("User found in the database: {}", email);
//        }
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        });
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
//    }
    
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
        if(user == null) {
            log.error("User not found in the database {}", email);
            throw new CUserNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        
	}

    @Override
    public User saveUser(User User) {
        log.info("Saving new user {} to the db", User.getEmail());
        return userRepository.save(User);
    }

    @Override
    public User getUser(String email) {
		return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


	@Override
	public UserRole saveUserRole(UserRole UserRole) {
		// TODO Auto-generated method stub
		log.info("Saving new role {} to the db", UserRole.getRoleName());
        return userRoleRepository.save(UserRole);
	}

	@Override
	public void grantUserRoleToUser(String email, String UserRoleName) {
		// TODO Auto-generated method stub
		log.info("Grant new role {} to {}", UserRoleName, email);
        User User = userRepository.findByEmail(email);
        UserRole role = (UserRole) userRoleRepository.findByRoleName(UserRoleName);

        User.getRole().add(role);
		
	}

}
