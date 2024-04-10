package org.triumers.newsnippetback.domain.aggregate.enums;

public enum UserRole {
    USER,
    MANAGER,
    ADMIN;

    public UserRole getUserRole(String roleName) {
        if (roleName.equals("USER")) {
            return USER;
        }
        if (roleName.equals("MANAGER")) {
            return MANAGER;
        }
        return ADMIN;
    }
}
