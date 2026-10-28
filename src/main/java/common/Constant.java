package common;

public class Constant {

    // WEBSITE URL
    public static final String MAILBOX_URL = "https://www.guerrillamail.com/inbox";
    public static final String RAILWAY_URL = "http://saferailway.somee.com/";

    // LOGIN ACCOUNT
    public static final String LOGIN_USERNAME = "atapg@sharklasers.com";
    public static final String LOGIN_PASSWORD = "123123123";

    // REGISTER ACCOUNT
    public static final String REGISTER_PID = "12345678";
    public static final String REGISTER_PASSWORD = "12345678";

    // INVALID INPUT VALUES
    public static final String BLANK_INPUT = "";
    public static final String INACTIVATED_EMAIL = "abc@gmail.com";
    public static final String INVALID_PASSWORD = "123";
    public static final String WRONG_PASSWORD = "abcdefghi";
    public static final String INVALID_PID = "12";

    // EMAIL KEYWORDS
    public static final String CONFIRM_EMAIL_KEYWORD = "confirm";
    public static final String RESET_MAIL_KEYWORD = "Please reset your password";

    // MESSAGES
    public static final String WELCOME_MESSAGE = "Welcome " + LOGIN_USERNAME;

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

    public static final String INVALID_PASSWORD_WARNING =
            "Invalid password length";

    public static final String INVALID_PID_WARNING =
            "Invalid ID length";

    public static final String REGISTER_CONFIRM_MESSAGE =
            "Registration Confirmed! You can now log in to the site.";

    public static final String SAME_RESET_PASSWORD_MESSAGE =
            "The new password cannot be the same with the current password";

    public static final String ERROR_RESET_PASSWORD_MESSAGE =
            "Could not reset password. Please correct the errors and try again.";

    public static final String UNMATCH_PASSWORD_MESSAGE =
            "The password confirmation did not match the new password.";

}
