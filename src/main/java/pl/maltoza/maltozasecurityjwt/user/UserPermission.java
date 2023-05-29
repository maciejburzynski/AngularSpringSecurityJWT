package pl.maltoza.maltozasecurityjwt.user;

import lombok.Getter;

@Getter
public enum UserPermission {
    ADMIN_READ("admin-resource:read"),
    USER_READ("user-resource:read");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }
}
