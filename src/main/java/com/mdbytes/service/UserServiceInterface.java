package com.mdbytes.service;

import com.mdbytes.model.Role;
import com.mdbytes.model.User;

import java.util.List;

public interface UserServiceInterface {

    User createUser(User user);

    boolean addUserRole(int roleId, int userId);

    boolean validateUser(String email, String password);

    User updateUser(User user);

    boolean deleteUserRole(int userId, int roleId);

    boolean deleteRole(int id);

    boolean deleteUser(Integer id);

    User getUserById(Integer id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<Role> getUserRoles(Integer userId);

    void deleteUserByEmail(String mail);
}
