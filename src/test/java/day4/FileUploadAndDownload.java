package day4;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import javax.mail.FetchProfile;

import org.testng.annotations.Test;

public class FileUploadAndDownload {
	// Run jar file first
	// Single file upload (It will create 1 folder in C as Uploads)
	@Test
	public void singleFileUpload() {
		File file=new File("src/test/java/day4/lord-of-the-universe-jagannath_1024-768.jpg");
		given()
			.multiPart(file)
			.contentType("multipart/form-data") // Mandatory
		.when()
			.post("http://localhost:8080/uploadFile")
		.then()
			.statusCode(200)
			.body("fileName", equalTo("lord-of-the-universe-jagannath_1024-768.jpg"))
			.log().body();
	}
	
	// Multiple file upload (It will create 1 folder in C as Uploads)
	@Test
	public void multipleFileUpload() {
		File file1=new File("src/test/java/day4/2021-08-11 (6).png");
		File file2=new File("src/test/java/day4/2021-08-10 (1).png");
		given()
			.multiPart("files",file1)
			.multiPart("files",file2)
			.contentType("multipart/form-data") // Mandatory
		.when()
			.post("http://localhost:8080/uploadMultipleFiles")
		.then()
			.statusCode(200)
			.body("[0].fileName", equalTo("2021-08-11 (6).png"))
			.body("[1].fileName", equalTo("2021-08-10 (1).png"))
			.log().body();
	}
	
	// Download file
	@Test
	public void downloadFile() {
		given()
			.pathParam("fileName", "2021-08-11 (6).png")
		.when()
			.get("http://localhost:8080/downloadFile/{fileName}")
		.then()
			.statusCode(200)
			.log().body();
	}
}
