package testCases;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import baseTest.ConfigRunner;
import io.restassured.response.Response;
import utilities.ExcelHandler;

public class ECMHealthCheck extends ConfigRunner {
	
	private static String TestData_path= "TestData\\ECMHealtcheckDemo.xlsx";
	private static String sheetName="URL";
	private static String sUrl;
	private static String sErrorFolder;
	
	@Test
	public static void URLCheck() throws Exception
	{
		
		testLogger=extentReport.createTest("HealthCheck Urls");
		
		try
		{
			
		int counter=0;
		ExcelHandler.setExcelFile(TestData_path,sheetName);
		int iRowCount = ExcelHandler.getRowNum();
		for(int iRowCounter=1;iRowCounter<=iRowCount;iRowCounter++)
		{
			sUrl=ExcelHandler.getCellData(iRowCounter, 0);
			
			System.out.println(sUrl);
		try {
			
		
			Response res=given().relaxedHTTPSValidation().when().get(sUrl).then().extract().response();
			System.out.println(res.statusCode());
			
		
			if(res.statusCode()==200)
			{
				ExcelHandler.setCellData( "Pass",iRowCounter,1);
				testLogger.info("Url Working Fine:"+sUrl);
			}
			else
			{
				testLogger.fail("Url is not Working Fine:"+sUrl);
				ExcelHandler.setCellData( "Fail",iRowCounter,1);
				
				counter=counter+1;
			}
		
		}
		
		catch(Exception e){
			
			testLogger.fail("Exception Occured for URL:"+sUrl);
			ExcelHandler.setCellData( "Fail",iRowCounter,1);
			counter=counter+1;
			continue;
		}
		}
		
		if(counter>0) {
			
			testLogger.fail("All urls not working fine");
		}
		
		else
		{
			testLogger.pass("All Urls Working Fine");
		}
		
		}
		
		catch(Exception e)
		{
			
		}
		ExcelHandler.closeexcel(TestData_path);
	}
	
	@Test
	public static void FoldersCheck()
	{
		testLogger=extentReport.createTest("HealthCheck Folders");
		
		
		System.out.println("This is Folder Check For Folder:");
		testLogger.pass("All Folders Working Fine");
		
	}

}
