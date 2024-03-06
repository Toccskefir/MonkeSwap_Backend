package hu.wv.MonkeSwapBackend.enums;

public enum ItemCategory {
    VEHICLE,
    HOME,
    HOUSEHOLD,
    ELECTRONICS,
    FREETIME,
    SPORT,
    FASHION,
    COLLECTIBLES,
    PETS,
    OTHER;

    public static ItemCategory findByName(String name) {
        ItemCategory result = null;
        for (ItemCategory category : values()) {
            if (category.name().equalsIgnoreCase(name)) {
                result = category;
                break;
            }
        }
        return result;
    }
}
