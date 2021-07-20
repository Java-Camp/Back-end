package com.jcf.persistence.model;

public enum Permission {
    USERS_READ("user:read"),
    USERS_WRITE("user:write");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}