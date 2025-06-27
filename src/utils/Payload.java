package utils;

import POJO.User;
import POJO.UserDetails;

public class Payload {

    public static User generateUser() {
        String username = TestDataGenerator.generateRandomUsername();
        String password = TestDataGenerator.generateRandomPassword();
        return new User(username, password);
    }

    public static String userCredentials(User user) {
        return "{\n"
                + "  \"username\": \"" + user.username + "\",\n"
                + "  \"password\": \"" + user.password + "\"\n"
                + "}";
    }

    public static String userDetails() {
        UserDetails user = new UserDetails(); // creates and initializes data in constructor
        
        return "{\n"
            + "  \"first_name\": \"" + user.getFirstName() + "\",\n"
            + "  \"last_name\": \"" + user.getLastName() + "\",\n"
            + "  \"date_of_birth\": \"" + user.getDob() + "\",\n"
            + "  \"initial_deposit\": " + user.getInitialDeposit() + "\n"
            + "}";
    }

}
