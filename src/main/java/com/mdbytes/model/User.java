package com.mdbytes.model;

import java.sql.Date;
import java.util.List;

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String cityAddress;
    private String state;
    private String zipCode;
    private String emailAddress;
    private String password;
    private String username;
    private String phoneNumber;
    private Date birthDate;
    private boolean isActive;
    private Date createdOn;
    private Date updatedOn;

    private List<Role> roles;

    public User() {

    }

    public User(String firstName, String lastName, String streetAddress, String cityAddress, String state, String zipCode, String emailAddress, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.cityAddress = cityAddress;
        this.state = state;
        this.zipCode = zipCode;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.streetAddress = streetAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.cityAddress = cityAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.zipCode = zipCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.emailAddress = emailAddress;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.updatedOn = new Date(System.currentTimeMillis());
        this.birthDate = birthDate;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> addRole(Role role) {
        roles.add(role);
        return roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", birthDate=" + birthDate +
                ", isActive=" + isActive +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
