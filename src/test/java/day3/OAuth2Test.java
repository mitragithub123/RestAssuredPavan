package day3;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

/*
1) From the application (Manual process)
1) Client ID
2) Client Secret

2) Send Post request for getting token
POST https://api.imgur.com/oauth2/token
	ClientID
	Client secret
	tokenURL
	Redirect URL
	Grant type
	Authorization code

you will get token once POST request is successful.

3) Use Token to do API call ( Get request).

*/
public class OAuth2Test {
	@Test
	void verifyOAuth2Authentication() {

		String clientId = "cff93d24167b033";
		String clientSecret = "ac85c1a5bc7e775cfbcd5b40188a2aa3b9be68d2";
		String redirectUri = "https://www.getpostman.com/oauth2/callback";
		String grantType = "authorization_code";
		String authorizationCode = "4c91c2e0de4cc9fa95ddb6e3fd0df11cc29ef739"; // Replace with actual authorization code

		String token=given()
			.formParam("client_id",clientId)
			.formParam("client_secret", clientSecret)
			.formParam("grant_type", grantType)
            .formParam("code", authorizationCode) 
            .formParam("redirect_uri", redirectUri)
        
		.when()
			.post("https://api.imgur.com/oauth2/token")
		.then()
			.statusCode(200)
			.extract().jsonPath().getString("access_token");
			
		System.out.println("Generated token: "+token);
		
		given()
			.auth().oauth2(token)   // OAuth2 authentication
		.when()
			.get("https://api.imgur.com/3/account/me/images")
		.then()
			.statusCode(200)
			.log().body();
		
	}
}
