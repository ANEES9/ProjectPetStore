package api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.poi.util.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class MyListeners implements ITestListener{

	ExtentReports extentReport;
	ExtentTest extentTest;
	
	InputStream is;
	byte[] sSBytes;
	
	public void onStart(ITestContext context) {
		System.out.println(" Execution of project text started");
		
		try {
			 extentReport = ExtentReporter.generateExtentReport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestStart(ITestResult result) {
		String testName =result.getName();
		
		 extentTest = extentReport.createTest(testName);
		extentTest.log(Status.INFO, testName +" started executed");
		
	}

	public void onTestSuccess(ITestResult result) {
		String testName =result.getName();
		
		 extentTest = extentReport.createTest(testName);
		extentTest.log(Status.PASS, extentTest +" Sucessfullu executed");
		
	}

	public void onTestFailure(ITestResult result) {
		String testName =result.getName();
		
		WebDriver driver = null;
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File srcScreenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		String destinationScreenshotPath = System.getProperty("user.dir")+"\\test-output\\"+testName+".png";
		
		try {
			FileHandler.copy(srcScreenshot, new File(destinationScreenshotPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//=================================================================================================================================
	
		// code for converting to Base64//
		
		try {
			 is =new FileInputStream(destinationScreenshotPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			 sSBytes = IOUtils.toByteArray(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String base64 = Base64.getEncoder().encodeToString(sSBytes);
//=================================================================================================================================
		
		extentTest.addScreenCaptureFromBase64String(base64);
		//extentTest.addScreenCaptureFromBase64String(destinationScreenshotPath);
		
		extentTest.log(Status.INFO,result.getThrowable());
		extentTest.log(Status.FAIL, extentTest +" Test failed");
		extentTest.log(Status.FAIL, testName +" Failed ");
		

	}

	public void onTestSkipped(ITestResult result) {
		String testName =result.getName();
		
		extentTest.log(Status.INFO,result.getThrowable());
		extentTest.log(Status.SKIP, testName +" Skipped ");
		
	
	}



	public void onFinish(ITestContext context) {
		extentReport.flush();
	}

}
