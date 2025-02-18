package test;

//TO validate json schema we use Json schema validator as external library 
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.*;
//Author Shubham 
//Dependencies added :- Rest Assured , TestNG, Hamcrest, Jackson-Databind, json schema validator 
import utils.methods;

public class Herokuapp_test {
	public static String token1;
	public static int  bookingID;
	static methods me = new methods();
	
	@Test (priority=1,description = "Test case to check health of the API ")
	public static void HealthCheck() {
		Response response = RestAssured.get("https://restful-booker.herokuapp.com/ping");
		
		System.out.println(response.asString());
		
		String resSt=
		given().log().all().when().get("https://restful-booker.herokuapp.com/ping")
		.then().log().all().assertThat().spec(methods.ResponseSpecification()).extract().response().asString();
		
		System.out.println(resSt);
	}




	@Test (priority =2,description = "Generate auth token " )
	public static void auth_token() {
		String token= 
		given().log().all().pathParam("path1","auth")
		.spec(methods.RequestSpecification())
		.body(methods.authBody("admin","password123"))
		.when().post("{path1}")
		.then().assertThat().spec(methods.ResponseSpecification())
		.assertThat()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("TokenGenerator.json"))
		.log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(token);
		
		 token1= js.get("token");
		
		System.out.println(token1);
		
		
}
	@Test (priority = 3 , description = "Creating booking ")
	public static void creatBooking() throws IOException {
		
		
		ArrayList al = me.readExcelData("data", "John");
		
		HashMap hma= me.herokuapp_body("data", "John");
		String Response =
		given().log().all().pathParam("path2", "booking")
		.spec(methods.RequestSpecification())
		.body(hma)
		.when().post("{path2}")
		.then()
		.body("booking.firstname", equalTo(al.get(0))) 
		.body("booking.lastname", equalTo(al.get(1))) 
		.body("booking.totalprice", equalTo(((Double) al.get(2)).intValue()))
		.body("booking.depositpaid", equalTo(al.get(3)))
		.body("booking.additionalneeds", equalTo(al.get(4)))
		.body("booking.bookingdates.checkin", equalTo(al.get(5)))
		.body("booking.bookingdates.checkout", equalTo(al.get(6)))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createBooking.json"))
		.log().all()
		.assertThat().spec(methods.ResponseSpecification()).extract().response().asString();
		
		JsonPath js = new JsonPath(Response);
		
		bookingID = 	js.get("bookingid");
	}
	@Test(priority = 4 , description = "Get booking via ID ")
	
	public static void getBookingviaID() throws IOException {
		ArrayList al = me.readExcelData("data", "John");
		
		HashMap hma= me.herokuapp_body("data", "John");
		given().log().all().pathParam("path3", "booking")
		.pathParam("path4", bookingID)
		.header("Accept","application/json")
		.spec(methods.RequestSpecification())
		
		.when().get("{path3}/{path4}")
		.then().log().all()
		.body("firstname", equalTo(al.get(0))) 
		.body("lastname", equalTo(al.get(1))) 
		.body("totalprice", equalTo(((Double) al.get(2)).intValue()))
		.body("depositpaid", equalTo(al.get(3)))
		.body("additionalneeds", equalTo(al.get(4)))
		.body("bookingdates.checkin", equalTo(al.get(5)))
		.body("bookingdates.checkout", equalTo(al.get(6)))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetBookingViaID.json"))
		.assertThat().spec(methods.ResponseSpecification()).extract().response().asString();;
	}
	
	@Test (priority = 5, description = "Update the booking it required token also depenedcy on case 1 ")
	
	public static void updateBooking() throws IOException {
		ArrayList al = me.readExcelData("data", "Alice");
		
		HashMap hma= me.herokuapp_body("data", "Alice");
		String Response =
				given().log().all().pathParam("path3", "booking")
				.header("Cookie","token="+token1+"")
				.pathParam("path4", bookingID)
				.header("Accept","application/json")
				.spec(methods.RequestSpecification())
				.body(hma)
				.when().put("{path3}/{path4}")
				.then().log().all()
				.body("firstname", equalTo(al.get(0))) 
				.body("lastname", equalTo(al.get(1))) 
				.body("totalprice", equalTo(((Double) al.get(2)).intValue()))
				.body("depositpaid", equalTo(al.get(3)))
				.body("additionalneeds", equalTo(al.get(4)))
				.body("bookingdates.checkin", equalTo(al.get(5)))
				.body("bookingdates.checkout", equalTo(al.get(6)))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("updateBooking.json"))
				.assertThat().spec(methods.ResponseSpecification()).extract().response().asString();
				
				JsonPath js = new JsonPath(Response);
	}
	
	@Test (priority =6, description = "Patch update partial update")
	
	public static void patchUpdate() throws IOException {
		ArrayList al = me.readExcelData("data", "Emma");
		ArrayList al2 = me.readExcelData("data", "Alice");
		
		HashMap hma= me.hePatch_body("data", "Emma");
		String Response =
				given().log().all().pathParam("path3", "booking")
				.header("Cookie","token="+token1+"")
				.pathParam("path4", bookingID)
				.header("Accept","application/json")
				.spec(methods.RequestSpecification())
				.body(hma)
				.when().patch("{path3}/{path4}")
				.then().log().all()
				.body("firstname", equalTo(al.get(0))) 
				.body("lastname", equalTo(al.get(1))) 
				.body("totalprice", equalTo(((Double) al2.get(2)).intValue()))
				.body("depositpaid", equalTo(al.get(3)))
				.body("additionalneeds", equalTo(al2.get(4)))
				.body("bookingdates.checkin", equalTo(al2.get(5)))
				.body("bookingdates.checkout", equalTo(al2.get(6)))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("updatePartialBooking.json"))
				.assertThat().spec(methods.ResponseSpecification()).extract().response().asString();
	}
	
	@Test (priority = 7 , description = "deleting the request ")
	
	public static void delete () {
		String deleteText=
		given().log().all().pathParam("path3", "booking")
		.header("Cookie","token="+token1+"")
		.pathParam("path4", bookingID)
		.header("Accept","application/json")
		.spec(methods.RequestSpecification())
		
		.when().delete("{path3}/{path4}")
		.then().log().all().assertThat().spec(methods.ResponseSpecification()).extract().response().asString();
		
		Assert.assertEquals(deleteText, "Created");
	}
	
	
	@Test (priority = 8 , description = "deleting the request ")
	public static void getbookings() 
	{
		given().log().all().when().get("https://restful-booker.herokuapp.com/booking")
		.then().log().all()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetBookingsID.json"));
	}
	
	
}