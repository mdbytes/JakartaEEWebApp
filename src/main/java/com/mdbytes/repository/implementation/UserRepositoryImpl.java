package com.mdbytes.repository.implementation;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mdbytes.model.Role;
import com.mdbytes.model.User;
import com.mdbytes.repository.UserRepositoryInterface;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryInterface {

    private Connection myConn = null;
    private CallableStatement cs = null;
    private ResultSet myRs = null;
    private Database db;

    public UserRepositoryImpl() {
        this.db = new Database();
    }

    @Override
    public User createUser(User user) {
        int rows = 0;

        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_create_user(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            cs.setString(1, user.getFirstName());
            cs.setString(2, user.getLastName());
            cs.setString(3, user.getStreetAddress());
            cs.setString(4, user.getCityAddress());
            cs.setString(5, user.getState());
            cs.setString(6, user.getZipCode());
            cs.setString(7, user.getEmailAddress());
            cs.setString(8, BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
            cs.setString(9, user.getPhoneNumber());
            cs.setString(10, user.getUsername());
            cs.setBoolean(11, user.isActive());
            cs.setDate(12, (Date) user.getBirthDate());
            cs.setDate(13, user.getCreatedOn());
            cs.setDate(14, user.getUpdatedOn());

            cs.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }

        User savedUser = getUserByEmail(user.getEmailAddress());

        addUserRole(1, savedUser.getId());

        return savedUser;
    }

    @Override
    public boolean addUserRole(int role, int user) {
        int rows = 0;
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_create_user_role(?,?)");
            cs.setInt(1, user);
            cs.setInt(2, role);
            rows = cs.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }

        return rows != 0;
    }

    @Override
    public boolean validateUser(String email, String password) {
        User user = getUserByEmail(email);
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        // result.verified == true
        return result.verified;
    }

    @Override
    public User updateUser(User user) {

        int rows = 0;
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_update_user(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            cs.setInt(1, user.getId());
            cs.setString(2, user.getFirstName());
            cs.setString(3, user.getLastName());
            cs.setString(4, user.getStreetAddress());
            cs.setString(5, user.getCityAddress());
            cs.setString(6, user.getState());
            cs.setString(7, user.getZipCode());
            cs.setString(8, user.getEmailAddress());
            cs.setString(9, BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
            cs.setString(10, user.getPhoneNumber());
            cs.setString(11, user.getUsername());
            cs.setBoolean(12, user.isActive());
            cs.setDate(13, (Date) user.getBirthDate());
            cs.setDate(14, user.getCreatedOn());
            cs.setDate(15, user.getUpdatedOn());

            rows = cs.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }

        User savedUser = getUserByEmail(user.getEmailAddress());

        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                addUserRole(role.getId(), user.getId());
            }
        }

        return savedUser;
    }

    @Override
    public boolean deleteUserRole(int userId, int roleId) {
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_delete_user_role(?,?)");
            cs.setInt(1, userId);
            cs.setInt(2, roleId);
            return 0 != cs.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }
        return false;
    }

    @Override
    public boolean deleteRole(int id) {
        int rows = 0;
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_delete_role(?)");
            cs.setInt(1, id);
            rows = cs.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }
        return rows != 0;
    }

    @Override
    public boolean deleteUser(Integer id) {
        int rows = 0;
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_delete_user(?)");
            cs.setInt(1, id);
            rows = cs.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }
        return rows != 0;
    }

    @Override
    public User getUserById(Integer id) {
        User user = null;
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_select_user_by_id(?)");
            cs.setInt(1, id);
            myRs = cs.executeQuery();
            if (myRs != null) {
                while (myRs.next()) {
                    user = new User();
                    user.setId(myRs.getInt(1));
                    user.setFirstName(myRs.getString(2));
                    user.setLastName(myRs.getString(3));
                    user.setStreetAddress(myRs.getString(4));
                    user.setCityAddress(myRs.getString(5));
                    user.setState(myRs.getString(6));
                    user.setZipCode(myRs.getString(7));
                    user.setEmailAddress(myRs.getString(8));
                    user.setPassword(myRs.getString(9));
                    user.setPhoneNumber(myRs.getString(10));
                    user.setUsername(myRs.getString(11));
                    user.setActive(myRs.getBoolean(12));
                    user.setBirthDate(myRs.getDate(13));
                    user.setCreatedOn(myRs.getDate(14));
                    user.setUpdatedOn(myRs.getDate(15));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_select_user_by_email(?)");
            cs.setString(1, email);
            myRs = cs.executeQuery();
            if (myRs != null) {
                while (myRs.next()) {
                    user = new User();
                    user.setId(myRs.getInt(1));
                    user.setFirstName(myRs.getString(2));
                    user.setLastName(myRs.getString(3));
                    user.setStreetAddress(myRs.getString(4));
                    user.setCityAddress(myRs.getString(5));
                    user.setState(myRs.getString(6));
                    user.setZipCode(myRs.getString(7));
                    user.setEmailAddress(myRs.getString(8));
                    user.setPassword(myRs.getString(9));
                    user.setPhoneNumber(myRs.getString(10));
                    user.setUsername(myRs.getString(11));
                    user.setActive(myRs.getBoolean(12));
                    user.setBirthDate(myRs.getDate(13));
                    user.setCreatedOn(myRs.getDate(14));
                    user.setUpdatedOn(myRs.getDate(15));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_select_all_users()");
            myRs = cs.executeQuery();
            if (myRs != null) {
                while (myRs.next()) {
                    User user = new User();
                    user.setId(myRs.getInt(1));
                    user.setFirstName(myRs.getString(2));
                    user.setLastName(myRs.getString(3));
                    user.setStreetAddress(myRs.getString(4));
                    user.setCityAddress(myRs.getString(5));
                    user.setState(myRs.getString(6));
                    user.setZipCode(myRs.getString(7));
                    user.setEmailAddress(myRs.getString(8));
                    user.setPassword(myRs.getString(9));
                    user.setPhoneNumber(myRs.getString(10));
                    user.setUsername(myRs.getString(11));
                    user.setActive(myRs.getBoolean(12));
                    user.setBirthDate(myRs.getDate(13));
                    user.setCreatedOn(myRs.getDate(14));
                    user.setUpdatedOn(myRs.getDate(15));
                    users.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }

        return users;
    }

    @Override
    public List<Role> getUserRoles(Integer userId) {
        List<Role> userRoles = new ArrayList<>();
        try {
            myConn = db.getConnection();
            cs = myConn.prepareCall("call sp_select_roles_by_user_id(?)");
            cs.setInt(1, userId);
            myRs = cs.executeQuery();
            if (myRs != null) {
                while (myRs.next()) {
                    Role role = new Role();
                    role.setId(myRs.getInt(1));
                    role.setName(myRs.getString(2));
                    userRoles.add(role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, cs, myRs);
        }
        return userRoles;
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = getUserByEmail(email);
        deleteUser(user.getId());
    }

    private void close(Connection myConn, CallableStatement cs, ResultSet myRs) {

        try {
            if (myRs != null) myRs.close();
            if (cs != null) cs.close();
            if (myConn != null) myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}


