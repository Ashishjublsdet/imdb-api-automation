package customReporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {

    static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal();

    public synchronized static ThreadLocal<ExtentTest> getTest() {
        return test;
    }

    public synchronized static void setTest(ExtentTest test) {
        getTest().set(test);
    }

    public static ExtentReports getInstance() {
        if (extentReports == null)
            createInstance();
        return extentReports;
    }

    public static ExtentReports createInstance() {
        String path = System.getProperty("user.dir") + "//src//main//resources//Report.html";
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(new File(path));
        extentSparkReporter.config().setDocumentTitle("Automation Report");
        extentSparkReporter.config().setReportName("API Automation");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        return extentReports;
    }
}
