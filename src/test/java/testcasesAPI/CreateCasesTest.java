package testcasesAPI;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import payLoads.CreateCases; 

public class CreateCasesTest {

	@Test
	public static void getCRnumber()
	{
		RestAssured.baseURI ="http://cit.qa.intranet/cit_bizagi/";
		 Map<String, String> authhdrs = new HashMap<String, String>();
		    authhdrs.put("SOAPAction", "http://tempuri.org/createCases");
		    authhdrs.put("Content-Type", "text/xml");
		    //authhdrs.put("charset", "UTF-8");
		
		    String response =given().auth()
				  .ntlm("x473215", "Lesedi@2022", "", "OMCORE").relaxedHTTPSValidation().headers(authhdrs).body(CreateCases.CreateCasesAPI()).log().all().
				when().post("WebServices/WorkflowEngineSOA.asmx").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		    JSONObject json = XML.toJSONObject(response);   
	        String jsonString = json.toString(4);  
	        System.out.println(jsonString);
	        
	        JsonPath js=new JsonPath(jsonString);
	        
	        String CENumberJson =js.getString("x[\"soap:Envelope\"][\"soap:Body\"].createCasesResponse.createCasesResult.processes.process.processRadNumber");
		
		
		
		    
		    XmlPath jsXpath= new XmlPath(response);//Converting string into xml path to assert
	
        
		
		String rate=jsXpath.get("processRadNumber").toString();
		String TempCR=rate.substring(0, 20);
		String CENumber =TempCR.substring(9, 20);
		
		
		
		
        
       
        System.out.println("rate returned is: " + CENumber );
        System.out.println("rate returned is: " + CENumberJson );
        
        
        
        
		
	}
}
