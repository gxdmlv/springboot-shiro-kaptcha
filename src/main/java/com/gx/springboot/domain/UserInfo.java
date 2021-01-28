package com.gx.springboot.domain;

import java.util.List;

/**
 * @author guoxing
 * @version V1.0
 * @Package com.gx.springboot.domain
 * @date 2021/1/27 15:43
 */
public class UserInfo {
    private String username;
    private String password;
    private Boolean available;
    private String role;
    private List<String> permissions;


    public UserInfo() {
    }

    public UserInfo(String username, String password, Boolean available, String role, List<String> permissions) {
        this.username = username;
        this.password = password;
        this.available = available;
        this.role = role;
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", available=" + available +
                ", role='" + role + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
