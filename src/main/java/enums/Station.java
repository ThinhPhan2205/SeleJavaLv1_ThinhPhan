package enums;

public enum Station {
    SAIGON("Sài Gòn"),
    DANANG("Đà Nẵng"),
    HUE("Huế"),
    QUANGNGAI("Quảng Ngãi"),
    NHATRANG("Nha Trang"),
    PHANTHIET("Phan Thiết");

    private final String value;

    Station(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
