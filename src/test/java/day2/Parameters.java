package day2;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Parameters {
	//Path parameter
	@Test
	public void pathParameter() {
		given()
			.pathParam("id", "5")
		.when()
			//.get("https://fakestoreapi.com/products/5")
			.get("https://fakestoreapi.com/products/{id}")
		.then()
			.statusCode(200)
			.log().body();
	}
	
	//Query parameter
	@Test
	public void queryParameters() {
		given()
			.queryParam("page", 2)
			.queryParam("id", 5)
			.header("x-api-key", "free_user_3EnQMnZdflCgDVuez6c2BNTjIRw")
		.when()
			//.get("https://reqres.in/api/users?page=2&id=5")
			.get("https://reqres.in/api/users")
		.then()
			.statusCode(200)
			.log().body();
	}
}
