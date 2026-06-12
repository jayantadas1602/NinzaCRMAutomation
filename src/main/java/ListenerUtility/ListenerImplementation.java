package ListenerUtility;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import BaseTest.BaseClass;

public class ListenerImplementation implements ITestListener,ISuiteListener
{
	public ExtentSparkReporter spark;
	public ExtentReports report;
	public ExtentTest test;
	@Override
	public void onStart(ISuite suite) {
		Date d = new Date();
		String newDate = d.toString().replace(" ","_").replace(":","_");
		spark = new ExtentSparkReporter("./AdvanceReports/report_"+newDate+".html");
		spark.config().setDocumentTitle("CRM Test Results");
		spark.config().setReportName("CRM Reports");
		spark.config().setTheme(Theme.DARK);
		
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows11");	
		
		//implement report config details here
		Reporter.log("Report Config",true);
		//extent report for advanced reporting
	}

	@Override
	public void onFinish(ISuite suite) {
		report.flush();
		Reporter.log("Report Backup",true);
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();//to get test case name
		test = report.createTest(testName);//in order to log test case name inside report
		// execute when test case getting started
		test.log(Status.INFO,"===="+testName+"Execution STARTED====");
		//
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();//to get test case name
		// execute when test case getting started
		test.log(Status.PASS,"===="+testName+"Execution SUCCESS====");	
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// execute when test case getting started
		String testName = result.getMethod().getMethodName();//to get test case name
		//to capture screenshots for failed test cases
		Date d = new Date();//from java.util
		String newDate = d.toString().replace(" ","_").replace(":","_");
		//no need to create object of base class
		//Another way is create static variable in base class.
		TakesScreenshot ts = (TakesScreenshot) BaseClass.sdriver;//driver present in base class
		//File temp = ts.getScreenshotAs(OutputType.FILE);
		 String temp = ts.getScreenshotAs(OutputType.BASE64);//for Extent Report
		test.addScreenCaptureFromBase64String(temp);//for extent report
		//File perm = new File("./Screenshots/"+testName+"_"+newDate+".png");//date used to randomise the filename 
		//in order to not override existing file
		//
		/*try {//try to copy sc to perm folder
			FileHandler.copy(temp, perm);
		} catch (IOException e) {//in case not failed no screenshots and to avoid exceptions we catch
			
			e.printStackTrace();
		}*/
		test.log(Status.FAIL,"===="+testName+"Execution FAILED====");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();//to get test case name
		// execute when test case getting started
		test.log(Status.SKIP,"===="+testName+"Execution SKIPPED====");
	}
}



