package testCases;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

import baseTest.ConfigRunner;
import io.restassured.response.Response;
import utilities.ExcelHandler;

public class BizagiHealthCheck extends ConfigRunner {

	private static String TestData_path= "TestData\\BizagiHealthCheck.xlsx";
	private static String sheetName="";
	private static String sUrl,sPath,name,sfolderNmae;
	private static String sErrorFolder;
	private static String Biz="jdbc:sqlserver://BIZAGIHAPRODLST.omlams.net;databaseName=CIT_Bizagi;integratedsecurity=true;";
	private static String BizInv="jdbc:sqlserver://BIZAGIHAPRODLST.omlams.net;databaseName=CIT_Investigations;integratedsecurity=true;";
	private static String BizRepl="jdbc:sqlserver://ZAOMPMISV001.omlams.net;databaseName=CIT_Investigations;integratedsecurity=true;";






	@Test
	public static void URLCheck() throws Exception
	{

		testLogger=extentReport.createTest("HealthCheck Urls");

		try
		{

			int counter=0;
			sheetName="URL";
			ExcelHandler.setExcelFile(TestData_path,sheetName);
			int iRowCount = ExcelHandler.getRowNum();
			for(int iRowCounter=1;iRowCounter<=iRowCount;iRowCounter++)
			{
				sUrl=ExcelHandler.getCellData(iRowCounter, 0);

				System.out.println(sUrl);
				try {


					Response res=given().relaxedHTTPSValidation().auth().basic("OMCORE\\XY58778","July@2021").when().get(sUrl).then().extract().response();
					System.out.println(res.statusCode());


					if(res.statusCode()==200 || res.statusCode()==401)
					{
						ExcelHandler.setCellData( "Pass",iRowCounter,1);
						testLogger.pass(sUrl);
					}
					else
					{
						testLogger.fail(sUrl);
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
	public static void FoldersCheck() throws Exception
	{
		testLogger=extentReport.createTest("HealthCheck Folders");
		sheetName="Logs";
		int counter=0;
		ExcelHandler.setExcelFile(TestData_path,sheetName);
		int iRowCount = ExcelHandler.getRowNum();
		for(int iRowCounter=1;iRowCounter<=iRowCount;iRowCounter++)
		{
			sPath=ExcelHandler.getCellData(iRowCounter, 1);
			sfolderNmae=ExcelHandler.getCellData(iRowCounter, 0);
			
			File directory = new File(sPath);
			if (directory.isDirectory()) {
				File[] files = directory.listFiles();

				if(files.length >0)
				{
					System.out.println("The directory " + directory.getPath() + " is not empty");
					System.out.println(directory.lastModified());

					File lastModifiedFile = files[0];
					for (int i = 1; i < files.length; i++) {
						if (lastModifiedFile.lastModified() < files[i].lastModified()) {
							lastModifiedFile = files[i];
						}
					}
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
					String lastmodified =sdf.format(lastModifiedFile.lastModified());

					System.out.println(lastmodified);

					Date date = new Date();
					date.getTime();
					long datediff = date.getTime()-lastModifiedFile.lastModified();

					System.out.println( date.getTime()-lastModifiedFile.lastModified());

					if(datediff<600000)
					{
						ExcelHandler.setCellData( "Pass",iRowCounter,2);
						testLogger.pass("latest Logs present  : "+sfolderNmae);
						System.out.println("Pass");


					}
					else
					{
						ExcelHandler.setCellData( "Fail",iRowCounter,2);
						testLogger.fail("No latest Logs present  : " +sfolderNmae);
						System.out.println("Pass");
						counter++;

					}



				}
				else
				{
					System.out.println("The directory " + directory.getPath() + " is empty");
					counter++;
				}



			}

		}

		if(counter>0) {

			testLogger.fail("There is issue with one of the Log file");
		}

		else
		{
			testLogger.pass("All Log Files Working Fine");
		}




		ExcelHandler.closeexcel(TestData_path);
	}


	@Test
	public static void SQLDatabaseCheck() throws Exception
	{
		testLogger=extentReport.createTest("HealthCheck SQLDatabase Check");
		sheetName="SQL";
		int counter=0;
		ExcelHandler.setExcelFile(TestData_path,sheetName);
		int iRowCount = ExcelHandler.getRowNum();
		for(int iRowCounter=1;iRowCounter<=iRowCount;iRowCounter++)
		{

			try {



				String connectionstr=ExcelHandler.getCellData(iRowCounter, 1);
				name =ExcelHandler.getCellData(iRowCounter, 0);
				String user="OMCORE\\XY58778";
				String password="July@2021";
				Connection con=DriverManager.getConnection(connectionstr,user,password);

				System.out.println("Database Login");

				Statement sqlStatement = con.createStatement();
				String sqlQuery = ExcelHandler.getCellData(iRowCounter, 2);
				ResultSet resSet = sqlStatement.executeQuery(sqlQuery);

				if(name.equalsIgnoreCase("last create"))
				{
					resSet.next();
					int count =resSet.getRow();
					System.out.println(count);

					if(count==1)
					{
						ExcelHandler.setCellData( "Pass",iRowCounter,3);
						testLogger.pass("Database Passed  : "+name);
						System.out.println("Pass");
					}
					else

					{
						ExcelHandler.setCellData( "Fail",iRowCounter,3);
						testLogger.fail("Database failed  : "+name);
						System.out.println("Fail");
						counter++;
					}

				}
				else if(name.equalsIgnoreCase("retry asynch"))
				{

				}
				else if (name.equalsIgnoreCase("GCS Val errors"))
				{
					resSet.next();
					int count =resSet.getRow();
					System.out.println(count);

					if(count<1)
					{
						ExcelHandler.setCellData( "Pass",iRowCounter,3);
						testLogger.pass("Database Passed  :  "+name);
						System.out.println("Pass");
					}
					else

					{
						ExcelHandler.setCellData( "Fail",iRowCounter,3);
						testLogger.fail("Database failed  :  "+name);
						System.out.println("Fail");
						counter++;
					}

				}

			}

			catch(Exception e)
			{
				ExcelHandler.setCellData( "Fail",iRowCounter,3);
				testLogger.fail("Database failed  :  "+name);
				e.printStackTrace();
				System.out.println(e);
				counter++;
			}


		}

		if(counter>0) {

			testLogger.fail("There is issue with one of the database");
		}

		else
		{
			testLogger.pass("All Databases Working Fine");
		}
		ExcelHandler.closeexcel(TestData_path);
	}

	@Test
	public static void SQLDatabaseSPCheck() throws Exception
	{
		testLogger=extentReport.createTest("HealthCheck SQLDatabase Stored Procedure Check");
		sheetName="SSP";

		int counter=0;
		ExcelHandler.setExcelFile(TestData_path,sheetName);
		int iRowCount = ExcelHandler.getRowNum();
		for(int iRowCounter=1;iRowCounter<=iRowCount;iRowCounter++)
		{
			String spName=ExcelHandler.getCellData(iRowCounter, 1);
			String connectionstr=ExcelHandler.getCellData(iRowCounter, 0);
			try {





				//String connectionstr="jdbc:sqlserver://ZAOMPMISV001.omlams.net;databaseName=CIT_Investigations;integratedsecurity=true;";
				String user="OMCORE\\XY58778";
				String password="July@2021";
				Connection con=DriverManager.getConnection(connectionstr,user,password);

				System.out.println("Database Login");

				String sqlQuery = "{call "+spName+"()}";
				CallableStatement  stmt = con.prepareCall(sqlQuery);
				boolean resSet = stmt.execute();
				stmt.close();

				System.out.println("Stored procedure called successfully!"); 
				if(resSet) {

					System.out.println("Stored procedure called successfully!"); 
					//System.out.println("This is Folder Check For Folder:");
					testLogger.pass("Stored procedure called successfully!");
					//reportStep("Stored Procedure " + spName + " Executed Sucessfully");
					ExcelHandler.setCellData( "Pass",iRowCounter,2);
					//testLogger.info("No latest Logs present"+sUrl);
					System.out.println("Pass");

				}

				else
				{
					//reportStep("Stored Procedure " + spName + " Not Executed Sucessfully");
					ExcelHandler.setCellData( "Fail",iRowCounter,2);
					testLogger.fail("Stored Procedure Failed:  "+spName);
					System.out.println("Fail");
					counter++;

				}

			}

			catch(Exception e)
			{
				testLogger.fail("Stored Procedure Failed:  "+spName);
				ExcelHandler.setCellData( "Fail",iRowCounter,2);
				counter++;

			}
		}

		if(counter>0) {

			testLogger.fail("There is issue with one of the Stored Procedure");
		}

		else
		{
			testLogger.pass("All Stored Procdedure Working Fine");
		}
		ExcelHandler.closeexcel(TestData_path);
	}



}
