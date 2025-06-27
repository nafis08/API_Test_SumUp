package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

	//List of names to create meaningful usernames
    static String[] firstNames = {
        "Alice", "Bob", "Charlie", "Diana", "Edward",
        "Fiona", "George", "Hannah", "Isaac", "Julia",
        "Kevin", "Lily", "Michael", "Nina", "Oscar",
        "Paula", "Quinn", "Rachel", "Steven", "Tina"
    };

    static String[] lastNames = {
            "Raegan", "Trump", "Sayikaran", "Khan", "Edward",
            "Fiona", "George", "Uddin", "Isaac", "Julia",
            "Kevin", "Lily", "Chowdhury", "Nina", "Oscar",
            "Paula", "Quinn", "Rachel", "Mishra", "Tina"
        };

    public static String generateRandomUsername() {
        Random random = new Random();
        String name = firstNames[random.nextInt(firstNames.length)];
        int number = 100 + random.nextInt(900);
        return name.toLowerCase() + number;
    }

    public static String generateRandomLastname() {
        Random random = new Random();
        String name = lastNames[random.nextInt(lastNames.length)];
        return name.toLowerCase();
    }

    public static String generateRandomPassword() {
        // Password: at least 1 capital, 1 number, 10 chars total
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            password.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return password.toString();
    }

    public static String generateRandomDateOfBirth() {
        // Define date range
        LocalDate startDate = LocalDate.of(1970, 1, 1);
        LocalDate endDate = LocalDate.of(1999, 12, 31);

        // Convert to epoch days
        long start = startDate.toEpochDay();
        long end = endDate.toEpochDay();

        // Generate random date
        long randomDay = ThreadLocalRandom.current().nextLong(start, end);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        // Format as "yyyy-MM-dd"
        return randomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static int generateRandomDiposit() {
    	Random rnd = new Random();
    	int deposit = rnd.nextInt();
    	return deposit;
    }
}
