package com.mdbytes.service.implementation;

import com.mdbytes.model.Role;
import com.mdbytes.model.User;
import com.mdbytes.service.UserServiceInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

class UserServiceImplTest {
    private static User firstUser;

    private static User savedUser;

    private static UserServiceInterface userService;

    @BeforeAll
    public static void beforeAllTests() {
        userService = new UserServiceImpl();
        firstUser = new User();
        firstUser.setFirstName("Martin");
        firstUser.setLastName("Dwyer");
        firstUser.setStreetAddress("168 1th Ave SW");
        firstUser.setCityAddress("Cedar Rapids");
        firstUser.setState("IA");
        firstUser.setZipCode("52404");
        firstUser.setPhoneNumber("3105551212");
        firstUser.setPassword("pickles4supper!");
        firstUser.setUsername("marty");
        firstUser.setEmailAddress("martin@mdbytes.com");
        firstUser.setBirthDate(Date.valueOf("1962-09-14"));
        firstUser.setCreatedOn(new Date(System.currentTimeMillis()));
        savedUser = userService.createUser(firstUser);
    }

    @Test
    void createUser() {
        Assertions.assertEquals(savedUser.getEmailAddress(), firstUser.getEmailAddress());
    }

    @Test
    void addUserRole() {
        userService.addUserRole(2, savedUser.getId());
        List<Role> userRoles = userService.getUserRoles(savedUser.getId());
        Assertions.assertTrue(userRoles.size() == 2, "Added Admin role to user");
    }

    @Test
    void validateUser() {
        boolean result = userService.validateUser("martin@mdbytes.com", "pickles4supper!");
        Assertions.assertTrue(result);
    }

    @Test
    void updateUser() {
        User testUser = savedUser;
        User newUser = userService.createUser(testUser);
        newUser.setCityAddress("Miami");
        userService.updateUser(newUser);
        User user = userService.getUserById(newUser.getId());
        Assertions.assertEquals(user.getCityAddress(), "Miami");
    }

    @Test
    void deleteUserRole() {
        User testUser = savedUser;

        int userId = testUser.getId();
        int firstRoleId = userService.getUserRoles(userId).get(0).getId();

        // remove first role
        userService.deleteUserRole(userId, firstRoleId);

        int newFirstRoleId = userService.getUserRoles(userId).get(0).getId();

        Assertions.assertNotEquals(firstRoleId, newFirstRoleId);

    }

    @Test
    void deleteRole() {

        // Needs work


    }

    @Test
    void deleteUser() {

        // Needs work
    }

    @Test
    void getUserById() {
        int id = savedUser.getId();
        User user = userService.getUserById(id);
        Assertions.assertEquals(user.getFirstName(), savedUser.getFirstName());
    }

    @Test
    void getUserByEmail() {
        User user = userService.getUserByEmail("martin@mdbytes.com");
        Assertions.assertEquals(user.getFirstName(), savedUser.getFirstName());
    }

    @Test
    void getAllUsers() {
        List<User> users = null;
        users = userService.getAllUsers();
        Assertions.assertTrue(users.size() > 0);
    }

    @Test
    void getUserRoles() {
        List<Role> roles = userService.getUserRoles(savedUser.getId());
        Assertions.assertTrue(roles.size() > 0);
    }
}