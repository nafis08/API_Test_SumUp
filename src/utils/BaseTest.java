package utils;


import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import POJO.User;

public class BaseTest {
    protected static User testUser;
    public static String apiKey;
    public static int userId;
    
    @BeforeSuite
    public void cleanAllureResultsFolder() {
        File allureResultsDir = new File("allure-results");
        if (allureResultsDir.exists()) {
            for (File file : allureResultsDir.listFiles()) {
                if (!file.delete()) {
                    System.out.println("Failed to delete: " + file.getAbsolutePath());
                }
            }
            System.out.println("Cleaned up allure-results folder.");
        } else {
            System.out.println("No existing allure-results folder to clean.");
        }
    }
    
    @AfterSuite
    public void generateAndOpenAllureReport() {
        try {
            // Generate Allure report
            Process generate = Runtime.getRuntime().exec("allure generate --clean");

            // Wait for the generation to complete
            generate.waitFor();

            // Open Allure report
            Process open = Runtime.getRuntime().exec("allure open");

            // Optional: wait for it to open before proceeding
            open.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
