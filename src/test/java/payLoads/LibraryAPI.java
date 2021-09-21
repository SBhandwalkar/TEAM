package payLoads;

public class LibraryAPI {
	
	
	
public static String AddAPI(String isbn,String aisle)
{
	
	String Payload ="{\r\n"
			+ "\r\n"
			+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
			+ "\"isbn\":\""+isbn+"\",\r\n"
			+ "\"aisle\":\""+aisle+"\",\r\n"
			+ "\"author\":\"John foe\"\r\n"
			+ "}\r\n"
			+ "" ;
	return  Payload;
}

public static String DeleteAPI(String ID)
{
	
	String Payload ="{\r\n"
			+ " \r\n"
			+ "\"ID\" : \""+ID+"\"\r\n"
			+ " \r\n"
			+ "} \r\n"
			+ "";
	return  Payload;
}
}
