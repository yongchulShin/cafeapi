package com.medicalip.cafe.service.user;

import java.util.List;

import com.medicalip.cafe.entity.User;
import com.medicalip.cafe.entity.UserRole;

public interface UserService {
	User saveUser(User User);
    User getUser(String email);
    List<User> getUsers();

    UserRole saveUserRole(UserRole UserRole);
    void grantUserRoleToUser(String email, String UserRoleName);
}
