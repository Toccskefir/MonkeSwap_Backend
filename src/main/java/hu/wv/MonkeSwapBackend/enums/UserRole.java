package hu.wv.MonkeSwapBackend.enums;

public enum UserRole {
    USER,
    ADMIN;

    public static UserRole findByName(String name) {
        UserRole result = null;
        for (UserRole role : values()) {
            if (role.name().equalsIgnoreCase(name)) {
                result = role;
                break;
            }
        }
        return result;
    }
}
