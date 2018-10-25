package Base;

import Helpers.ExtentManager;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import static Helpers.Common.BASE_PORT;
import static Helpers.Common.BASE_URI;

public class BaseRest {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

    @BeforeSuite
    public void beforeSuite() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = BASE_PORT;
        RestAssured.defaultParser = Parser.JSON;

        extent = ExtentManager.createInstance("target/extent.html");
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("target/extent.html");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("This is the Document Title");
        htmlReporter.config().setReportName("This is the name of the report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("HH:mm dd/MM/yyyy");
        //htmlReporter.config().setCSS("body:not(.default) {overflow: scroll !important;}");
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("OS NAME", System.getProperty("os.name"));
        extent.setSystemInfo("OS VERSION", System.getProperty("os.version"));
        extent.setSystemInfo("URL", BASE_URI);
        extent.setSystemInfo("PORT", String.valueOf(BASE_PORT));

//        extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
//        extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
    }

    @BeforeClass
    public synchronized void beforeClass() {
        ExtentTest parent = extent.createTest(getClass().getName());
        parentTest.set(parent);
    }

    @BeforeMethod
    public synchronized void beforeMethod(Method method) {
        ExtentTest child = parentTest.get().createNode(method.getName());
        child.assignCategory(method.getDeclaringClass().getSimpleName());
        test.set(child);
    }

    @AfterMethod
    public synchronized void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE)
            test.get().fail(result.getThrowable());
        else if (result.getStatus() == ITestResult.SKIP)
            test.get().skip(result.getThrowable());
        else
            test.get().pass("Test passed");

        extent.flush();
    }
}
