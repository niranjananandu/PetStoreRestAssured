package api.utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager extends TestUtilities implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;
    private Logger log;
    private String testName;
    private String methodName;
    private String pathname;

    @Override
    public void onStart(ITestContext context) {
        this.testName = context.getCurrentXmlTest().getName();
        this.log = LogManager.getLogger(testName);
        this.pathname = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "Reports" + File.separator + this.testName +
                File.separator + TestUtilities.getTodaysDate() + File.separator + "report" + TestUtilities.getSystemTime() + ".html";

        sparkReporter = new ExtentSparkReporter(pathname);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("API Testing - Pet Store");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", System.getProperty("user.name"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        this.methodName = result.getMethod().getMethodName();
        log.info("Starting test case: " + methodName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (extent != null) {
            test = extent.createTest(this.methodName);
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.PASS, "The test case passed is " + this.methodName);
        } else {
            log.error("ExtentReports instance is not initialized.");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (extent != null) {
            test = extent.createTest(this.methodName);
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.FAIL, "The test case failed is " + this.methodName);
            test.log(Status.INFO, "Failed cause is " + result.getThrowable());            
        } else {
            log.error("ExtentReports instance is not initialized.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (extent != null) {
            test = extent.createTest(this.methodName);
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.SKIP, "The test case skipped is " + this.methodName);
        } else {
            log.error("ExtentReports instance is not initialized.");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
            File newFile = new File(this.pathname);
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(newFile.toURI());
                } else {
                    log.warn("Desktop is not supported on this platform.");
                }
            } catch (IOException e) {
                log.error("Exception occurred while opening report: ", e);
            }
        } else {
            log.error("ExtentReports instance is not initialized.");
        }
    }
    
    
}