package log_methods;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class log_methods {

	@Test
	public static void log_methods() {
		Response response = given().when().log().all().get("https://www.google.com/");

		// log().evrything() it is not a correct method as per chat gpt we must use log
		// all also both delivers similar results

		// Print
		ResponseBody body = response.getBody(); // ouput type is String

		// System.out.println(body.prettyPrint()); //organised output with pretty print

		Map<String, String> cookies = response.getCookies(); // map output

		// System.out.println(cookies);

		for (Entry<String, String> entry : cookies.entrySet()) {
			// System.out.println("Key: " + entry.getKey() + ", Value: " +
			// entry.getValue());

		}
		Headers body2 = response.headers(); // outputtype is String

		// System.out.println(body2);

	}
}
