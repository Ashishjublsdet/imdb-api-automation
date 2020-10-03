package customListner;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import customReporter.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ReportListner implements ITestListener {
    public void onTestStart(ITestResult iTestResult) {
        ExtentTest extentTest = ExtentManager.getInstance().createTest(iTestResult.getMethod().getMethodName());
        ExtentManager.setTest(extentTest);
    }

    public void onTestSuccess(ITestResult iTestResult) {
        ExtentManager.getTest().get().assignCategory(iTestResult.getMethod().getRealClass().getSimpleName());
        ExtentManager.getTest().get().createNode(MarkupHelper.createLabel("Test passed", ExtentColor.GREEN).getMarkup());
        ExtentManager.getInstance().flush();
    }

    public void onTestFailure(ITestResult iTestResult) {
        ExtentManager.getTest().get().assignCategory(iTestResult.getMethod().getRealClass().getSimpleName());
        ExtentManager.getTest().get().createNode(MarkupHelper.createLabel("Test Failed", ExtentColor.RED).getMarkup())
                .fail(iTestResult.getThrowable());
        ExtentManager.getInstance().flush();
    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }
}
