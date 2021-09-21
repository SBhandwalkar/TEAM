package testCases;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import functionLib.RSAMapsPayloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class Apitest_Google_Maps {


	//@Test
	public static void getAddress()
	{
		

		RestAssured.baseURI="https://rahulshettyacademy.com";
		String res=given().filter(new io.restassured.filter.log.RequestLoggingFilter()).log().all().queryParam("key","qaclick123").header("Content-Type","application/json").body(RSAMapsPayloads.addLocation()).when().post("/maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js= new JsonPath(res);
		String placeID=js.getString("id");
		System.out.println(placeID);
	}
	@Test
	public static void getCookies()
	{
		
	    
		//String username = System.getenv("OM_USER");
		// String password = System.getenv("OM_PASS");
		 String username = "XY58778";
		 String password = "July@2021";
		 String domain = "omcore";
		 String host = "cit.dev.intranet";
		 String url = "http://cit.intranet/CIT_Bizagi/Rest/Authentication/User";
		 Response res = given()
		 .relaxedHTTPSValidation()
		 .auth()
		 .ntlm(username, password, "", domain)
		 .log()
		 .all()
		 .formParams(
		 "user",
		 username,
		 "password",
		 password,
		 "domain",
		 domain.toUpperCase(),
		 "loginOption",
		 "alwaysAsk"
		 )
		 .when()
		 .post(url)
		 .then()
		 .log()
		 .all()
		 .assertThat()
		 .statusCode(200)
		 .extract()
		 .response();
		 System.out.println(res.getCookie("ASP.NET_SessionId"));



	}

}
