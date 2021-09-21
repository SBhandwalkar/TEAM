package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;



public class ExtendReports{
	
	public static ExtentSparkReporter reporter;
	public static ExtentReports extent;
	public static ExtentTest testLogger;
	
	
	
	public static ExtentReports getReporter()
	{
		reporter=new ExtentSparkReporter(System.getProperty("user.dir")+"\\ExtentReports\\index.html");
		reporter.config().setReportName("NPW Automation");
		reporter.config().setDocumentTitle("Test Results");
		
		extent=new ExtentReports();
		extent.attachReporter(reporter);
		return extent;
	}
	
	public static String getScreenshotPath(String Test,WebDriver driver) throws IOException
	{
		
		
		TakesScreenshot screen = ((TakesScreenshot)driver);
		File SrcFile=screen.getScreenshotAs(OutputType.FILE);
		String dest=System.getProperty("user.dir")+"\\ExtentReports\\Screenshots\\"+now()+""+Test+".png";
		File DestFile =new File(dest);
		
		FileUtils.copyFile(SrcFile, DestFile);
		return dest;
		
		
	}
	
	private static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	

}
