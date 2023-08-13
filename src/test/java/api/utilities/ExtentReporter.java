package api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {
	
	public static ExtentReports generateExtentReport() throws IOException {
		
		ExtentReports extentReports = new ExtentReports();
		File extentReportFile =new File (System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentReport.html");
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);
		
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setReportName("Pet store API Results");
		sparkReporter.config().setDocumentTitle("Automated report");
		sparkReporter.config().setTimeStampFormat("dd/MM/YYYY  hh:mm:ss");
		
		extentReports.attachReporter(sparkReporter);
		
		FileInputStream fileInputStream = new FileInputStream(
				"D:\\Workspace-2\\ProjectPetStore2\\TestData\\config.properties");
		Properties properties = new Properties();
		properties.load(fileInputStream);

		extentReports.setSystemInfo("Application URL", properties.getProperty("url"));
		extentReports.setSystemInfo("Browser Info", properties.getProperty("browser"));
		extentReports.setSystemInfo("Tester Email", properties.getProperty("validEmail"));
		extentReports.setSystemInfo("Password", properties.getProperty("validPassword"));
		extentReports.setSystemInfo("Java Version", System.getProperty("Java.version"));
		
		return extentReports;
		
		
		
	}

}
