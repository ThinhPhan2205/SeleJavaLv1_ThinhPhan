package common;

import org.openqa.selenium.By;

public class Constant {

    // WEBSITE URL
    public static final String RAILWAY_URL = "http://saferailway.somee.com/";

    // LOGIN ACCOUNT
    public enum AccountToLogin {
        USERNAME("atapg@sharklasers.com"),
        PASSWORD("123123123");

        private final String value;

        AccountToLogin(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // REGISTER ACCOUNT
    public enum AccountToRegister {
        PID("12345678"),
        PASSWORD("12345678");

        private final String value;

        AccountToRegister(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // INVALID INFORMATION
    public enum InvalidInputs {
        BLANK_INPUT(""),
        INACTIVATED_EMAIL("abc@gmail.com"),
        INVALID_PASSWORD("123"),
        WRONG_PASSWORD("abcdefghi"),
        INVALID_PID("12");
        private final String value;

        InvalidInputs(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // GUERRILLAMAIL
    public static final String MAILBOX_URL = "https://www.guerrillamail.com/inbox";
    public static final String CONFIRM_EMAIL_SUBJECT = "Please confirm your account";
    public static final String CONFIRM_EMAIL_KEYWORD = "confirm";
    public static final String RESET_MAIL_KEYWORD = "Please reset your password";

    // MENU BAR TAB NAMES
    public enum MenuTab {
        REGISTER("Register"),
        LOGIN("Login"),
        BOOK_TICKET("Book ticket"),
        TIMETABLE("Timetable"),
        TICKET_PRICE("Ticket price"),
        HOME("Home"),
        FAQ("FAQ"),
        MY_TICKET("My ticket"),
        CHANGE_PASSWORD("Change password"),
        LOGOUT("Log out");

        private final String value;

        MenuTab(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // TABLE HEADERS
    public enum TableHeader {
        DEPART_STATION("Depart Station"),
        ARRIVE_STATION("Arrive Station"),
        SEAT_TYPE("Seat Type"),
        DEPART_DATE("Depart Date"),
        BOOK_TICKET("Book Ticket"),
        TICKET_AMOUNT("Amount"),
        CHECK_PRICE("Check Price"),
        OPERATION("Operation");

        private final String value;

        TableHeader(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // SEAT TYPES
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

    // SUPPPORTED STATIONS
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

    // NOTIFICATION MESSAGE
    public static final String WELCOME_MESSAGE = "Welcome " + AccountToLogin.USERNAME.getValue();
    public static final String CONFIRMATION_MESSAGE = "Ticket booked successfully!";
    public static final String WRONG_ACCOUNT_LOGIN_MESSAGE =
            "Invalid username or password. Please try again.";
    public static final String INVALID_LOGIN_VALUE_MESSAGE =
            "There was a problem with your login and/or errors exist in your form.";
    public static final String DISABLED_LOGIN_WARNING =
            "You have used 4 out of 5 login attempts. " +
                    "After all 5 have been used, you will be unable to login for 15 minutes.";
    public static final String INUSE_EMAIL_WARNING = "This email address is already in use.";
    public static final String INVALID_REGISTER_VALUE_MESSAGE =
            "There're errors in the form. Please correct the errors and try again.";
    public static final String REGISTER_CONFIRM_MESSAGE =
            "Registration Confirmed! You can now log in to the site.";
    public static final String ERROR_RESET_PASSWORD_MESSAGE =
            "The new password cannot be the same with the current password";

}
