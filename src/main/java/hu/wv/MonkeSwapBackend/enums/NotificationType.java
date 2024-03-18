package hu.wv.MonkeSwapBackend.enums;

public enum NotificationType {
    NOTIFICATION,
    WARNING;

    public static NotificationType findByName(String name) {
        NotificationType result = null;
        for (NotificationType state : values()) {
            if (state.name().equalsIgnoreCase(name)) {
                result = state;
                break;
            }
        }
        return result;
    }
}
