package hu.wv.MonkeSwapBackend.enums;

public enum ItemState {
    DISABLED,
    ENABLED;

    public static ItemState findByName(String name) {
        ItemState result = null;
        for (ItemState state : values()) {
            if (state.name().equalsIgnoreCase(name)) {
                result = state;
                break;
            }
        }
        return result;
    }
}
