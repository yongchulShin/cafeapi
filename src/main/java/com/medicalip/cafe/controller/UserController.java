package com.medicalip.cafe.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.medicalip.cafe.config.security.ExpiredRefreshTokenService;
import com.medicalip.cafe.config.security.JwtTokenProvider;
import com.medicalip.cafe.dto.response.SingleResult;
import com.medicalip.cafe.entity.User;
import com.medicalip.cafe.repository.UserJpaRepository;
import com.medicalip.cafe.repository.UserRoleRepository;
import com.medicalip.cafe.service.ResponseService;
import com.medicalip.cafe.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Tag(name = "User")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserJpaRepository userJpaRepository; // jpa 쿼리 활용
    private final UserService userService; // user Service
    private final UserRoleRepository userRolesRepository; // user Service
    private final JwtTokenProvider jwtTokenProvider; // jwt 토큰 생성
    private final ResponseService responseService; // API 요청 결과에 대한 code, message
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화
    private final ExpiredRefreshTokenService expiredRefreshTokenService; // 비밀번호 암호화
//	private final MessageSourceAccessor messageSourceAccessor;

    @Operation(summary = "로그인", description = "이메일 회원 로그인을 한다.")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "OK"),
    		@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
    		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping(value = "/login")
    public ResponseEntity<SingleResult> login(@Parameter(description = "회원ID : 이메일", required = true) @RequestParam String email,
    		@Parameter(description = "비밀번호", required = true) @RequestParam String password,
        HttpServletRequest request, HttpServletResponse response) {
    	
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User)authentication.getPrincipal();
        } else {
            try {
            	System.out.println("=================================================================");
				user = userJpaRepository.findByEmail(email);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (!passwordEncoder.matches(password,  user.getPassword())) {
                //아이디 비밀번호 미일치 시 처리할 로직
            	System.out.println("Not Match Password");
            	return new ResponseEntity<>(responseService.loginFail(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
        }

        if (user == null) {
            //유저객체를 제대로 불러오지 못할때 처리할 로직
        	System.out.println("Not User");
        	return new ResponseEntity<>(responseService.loginFail(HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String expiredToken = jwtTokenProvider.resolveRefreshToken(request);
        if (expiredToken != null && !expiredToken.isBlank()) {
            expiredRefreshTokenService.addExpiredToken(expiredToken);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        
        String accessToken = jwtTokenProvider.createToken(user.getEmail(), authorities);
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), authorities);
        
        //refreshToken 쿠키 저장
        Cookie refreshTokenCookie = new Cookie("refresh-token", refreshToken);
        response.addCookie(refreshTokenCookie);
        
        //accessToken Header set
        response.setHeader("access-token", accessToken);

        return new ResponseEntity<>(responseService.loginSuccess(accessToken), HttpStatus.OK);
    }

//	@Operation(summary = "가입", description = "회원가입을 한다.")
//    @PostMapping(value = "/signup")
//    public CommonResult signup(@Parameter(description = "회원ID : 이메일", required = true) @RequestParam String email,
//    		@Parameter(description = "비밀번호", required = true) @RequestParam String password,
//    		@Parameter(description = "이름", required = true) @RequestParam String name,
//    	    @Parameter(description = "권한", required = false) @RequestParam String roles) {
//    	
//    	boolean existMember = userJpaRepository.existsByEmail(email);
//
//        // 이미 회원이 존재하는 경우
//        if (existMember) return responseService.getFailResult(Constants.USERFOUND_CODE, Constants.USERFOUND_MSG);
//
//    	userJpaRepository.save(User.builder()
//                .email(email)
//                .password(passwordEncoder.encode(password))
//                .name(name)
//                .roles(UserRolesRepository.findByRoleName(roles))
//                .edtTime(LocalDateTime.now())
//                .regTime(LocalDateTime.now())
//                .build());
//
//        return responseService.getSuccessResult();
//    }
    
    @Operation(summary = "회원목록", description = "회원 리스트를 출력한다.")
    @GetMapping("/list")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
    
    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody SignUpForm signUpForm) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/user/save").toUriString());

        User appUser = signUpForm.toEntity();
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.getRole().add(userRolesRepository.findByRoleName("ROLE_USER"));

        return ResponseEntity.created(uri).body(userService.saveUser(appUser));
    }
    
    @PostMapping("/role")
    public ResponseEntity<?> grantRole(@RequestBody RoleToUserForm form) {
        userService.grantUserRoleToUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/token/refresh")
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String authorizationHeader = request.getHeader("access-token");
//
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            try{
//            	jwtTokenProvider.getAuthentication();
//                User user = userService.getUser(username);
//
//                String accessToken = JwtTokenProvider.
//                        .withSubject(user.getEmail())
//                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                        .withIssuer(request.getRequestURI().toString())
//                        .withClaim("roles", user.getRoles().stream().map(UserRoles::getRoleName).collect(Collectors.toList()))
//                        .sign(algorithm);
//
//
//                Map<String, String> tokens = new HashMap<>();
//                tokens.put("access_token", accessToken);
//                tokens.put("refresh_token", refreshToken);
//
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//            }catch (Exception e) {
//                response.setHeader("error", e.getMessage());
//                response.setStatus(FORBIDDEN.value());
//
//                Map<String, String> error = new HashMap<>();
//                error.put("error_message", e.getMessage());
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(), error);
//            }
//        } else {
//            throw new RuntimeException("Refresh token is missing");
//        }
//    }

}

@Data
class RoleToUserForm {
    private String email;
    private String roleName;
}

@Data @Builder
class SignUpForm {
    private String email;
    private String name;
    private String password;

    public User toEntity() {
        return User
                .builder().email(this.email).name(this.name).password(this.password)
                .role(new ArrayList<>())
                .build();
    }
}