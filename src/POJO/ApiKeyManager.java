package POJO;

public class ApiKeyManager {

	private static String apiKey;

	public static String getApiKey() {
		return apiKey;
	}

	public static void setApiKey(String apiKey) {
		ApiKeyManager.apiKey = apiKey;
	}


}
