package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationApiKey {

    @JsonProperty("api_key") // Maps JSON "api_key" to this Java field
    private String apiKey;

    // Getter
    public String getApiKey() {
        return apiKey;
    }

    // Setter
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
