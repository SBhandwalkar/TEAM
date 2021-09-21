package testcasesAPI;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payLoads.LibraryAPI;
import utilities.JsonHandling;

import static io.restassured.RestAssured.*;

public class LibraryAPITest {

	public static String  sID;
	
	@Test
	public static void AddBook_API_Test()
	{
		
		RestAssured.baseURI ="http://216.10.245.166";
		String response =given().relaxedHTTPSValidation().header("Content-Type","Application/json").body(LibraryAPI.AddAPI("pack2","5634")).
		when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=JsonHandling.convertToJson(response);
		sID=js.get("ID");
		
		GetBook_API_Test(sID);
		DeleteBook_API_Test(sID);
	}
	
	
	public static void DeleteBook_API_Test(String ID)
	{
		RestAssured.baseURI ="http://216.10.245.166";
		String response =given().relaxedHTTPSValidation().header("Content-Type","Application/json").body(LibraryAPI.DeleteAPI(ID)).
		when().post("/Library/DeleteBook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		if(response.contains("book is successfully deleted"))
		{
			System.out.println("Pass");
		}
		else
		{
			System.out.println("Fail");
		}
		
	}
	
	
	public static void GetBook_API_Test(String ID)
	{
		RestAssured.baseURI ="http://216.10.245.166";
		String response =given().relaxedHTTPSValidation().queryParam("ID",ID).when().get("/Library/GetBook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);
	}
}
