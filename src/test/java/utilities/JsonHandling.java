package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;

public class JsonHandling {
	
	
	public static String getJsonFile(String filePath) throws IOException
	{
		
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}
	
	public static JsonPath convertToJson(String response)
	{
		
		JsonPath js= new JsonPath(response);
		return js;
	}

}
