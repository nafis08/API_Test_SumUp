package POJO;

import utils.BaseTest;
import utils.TestDataGenerator;

public class UserDetails extends BaseTest {

    private String firstName;
    private String lastName;
    private String dob;
    private int initialDeposit;

    // Constructor to initialize all fields
    public UserDetails() {
        this.firstName = testUser.username;
        this.lastName = TestDataGenerator.generateRandomLastname();
        this.dob = TestDataGenerator.generateRandomDateOfBirth();
        this.initialDeposit = TestDataGenerator.generateRandomDiposit();
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(int initialDeposit) {
        this.initialDeposit = initialDeposit;
    }
}
