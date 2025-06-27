package utils;
import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.configure()
            .filename(".env")  // to fetch base URL from the .env file
            .load();

    public static String getBaseUrl() {
        return dotenv.get("BASE_URL");
    }
}

