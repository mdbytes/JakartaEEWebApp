package com.mdbytes.service.implementation;

import com.mdbytes.model.Role;
import com.mdbytes.model.User;
import com.mdbytes.repository.UserRepositoryInterface;
import com.mdbytes.repository.implementation.UserRepositoryImpl;
import com.mdbytes.service.UserServiceInterface;

import java.util.List;

public class UserServiceImpl implements UserServiceInterface {

    private UserRepositoryInterface userRepository;

    public UserServiceImpl() {

        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public boolean addUserRole(int roleId, int userId) {
        return userRepository.addUserRole(roleId, userId);
    }

    @Override
    public boolean validateUser(String email, String password) {
        return userRepository.validateUser(email, password);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUserRole(int userId, int roleId) {
        return userRepository.deleteUserRole(userId, roleId);
    }

    @Override
    public boolean deleteRole(int id) {
        return userRepository.deleteRole(id);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<Role> getUserRoles(Integer userId) {
        return userRepository.getUserRoles(userId);
    }

    @Override
    public void deleteUserByEmail(String email) {
        userRepository.deleteUserByEmail(email);
    }
}
