package enums;

public enum SeatType {
    HARD_SEAT("Hard seat"),
    SOFT_SEAT("Soft seat"),
    SOFT_SEAT_AIR("Soft seat with air conditioner"),
    HARD_BED("Hard bed"),
    SOFT_BED("Soft bed"),
    SOFT_BED_AIR("Soft bed with air conditioner");

    private final String value;

    SeatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
