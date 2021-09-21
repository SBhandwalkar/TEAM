package baseTest;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utilities.Browser;

public class ConfigRunner {

	public static String sBrowser = "";	
	public static WebDriver driver;
	public static String sChromeDriverPath = "";
	public static ExtentSparkReporter reporter;
	public static ExtentReports extentReport;
	public static ExtentTest testLogger;



	//Browserstack members
	public static String sbStackUserName="";
	public static String sbStackpasskey="";
	public static String sbStackOS="";
	public static String sbStackOs_version="";
	public static String sbStackBrowser="";
	public static String sbStackBrowser_version="";
	public static String sbStackSelenium_version="";

	
	@BeforeSuite
	public static void InitSetup() throws IOException {
		
		reporter=new ExtentSparkReporter(System.getProperty("user.dir")+"\\ExtentReports\\index.html");
		//reporter.config().setReportName("NPW Automation");
		//reporter.config().setDocumentTitle("Test Results");
		reporter.loadXMLConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
		
		extentReport=new ExtentReports();
		extentReport.attachReporter(reporter);
		
		
		
	}
	
	@AfterMethod
	public void tearDownMethod(ITestResult result)
	{
		
		/*
		 * if(result.getStatus()==ITestResult.FAILURE) { testLogger.fail("Test Fail"); }
		 * 
		 * else if(result.getStatus()==ITestResult.SUCCESS) {
		 * testLogger.pass("Test Execution Completed"); }
		 */
		extentReport.flush();
	}
	
	@BeforeClass
	public static void initBrowser() throws IOException
	{
		try
		{

			loadPropertyFile();
			/*
			 * Authenticator.setDefault(new ProxyAuthenticator("OMCORE\\XY58778",
			 * "July@2021")); System.setProperty("http.proxyHost", "fwdproxy.za.omlac.net");
			 * System.setProperty("http.proxyPort", "8080");
			 * System.setProperty("https.proxyHost", "fwdproxy.za.omlac.net");
			 * System.setProperty("https.proxyPort", "8080");
			 */

			if(sBrowser.equalsIgnoreCase("chrome") || sBrowser.equalsIgnoreCase("cr") || sBrowser.equalsIgnoreCase("chromeHeadless"))
				driver = Browser.openBrowser(sBrowser);

			else if(sBrowser.equalsIgnoreCase("browserstack") )
			{

				driver = Browser.openBrowserStack(sbStackUserName,sbStackpasskey,sbStackOS,sbStackOs_version,sbStackBrowser,sbStackBrowser_version);
			}

			else
			{

			}

			//set the default wait and timeout values 
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


	}






	public static void loadPropertyFile() throws IOException
	{
		
		Properties p = new Properties();
		FileReader reader;


		try
		{
			reader = new FileReader("config.properties");
			p.load(reader);
			reader.close();
			sBrowser=p.getProperty("Browser");
			sbStackUserName=p.getProperty("bStackUserName");
			sbStackpasskey=p.getProperty("bStackpasskey");
			sbStackOS=p.getProperty("bStackOS");
			sbStackOs_version=p.getProperty("bStackOs_version");
			sbStackBrowser=p.getProperty("bStackBrowser");
			sbStackBrowser_version=p.getProperty("bStackBrowser_version");




		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}

	}

	@AfterClass(alwaysRun = true)
	public static void closeBrowser() throws IOException {
		String methodID = "closeBrowser";
		if(sBrowser.equalsIgnoreCase("browserstack")) {
			driver.quit();
			System.out.println("Browserstack closed successfully");
		}
		else
		{
			driver.close();
			if (!sBrowser.equalsIgnoreCase("ff") && !sBrowser.equalsIgnoreCase("firefox")) { 
				Browser.closeCurrentBrowser(driver);
			}
			else {
				Runtime.getRuntime().exec("taskkill /T /F /IM geckodriver.exe");
			}
		}

		System.out.println(methodID + ": the WebDriver '" + sBrowser + "' browser was closed.");



	}

}
