package com.algoworks.automationmobile.base_RR;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.File;

public class AllureTestListener implements ITestListener {

    @Override
    public void onStart(ITestContext iTestContext) {
        // No specific setup required at the start of the test context
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        // No specific action required at the end of the test context
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        // No specific action required at the start of the test method
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        // Log test success to Allure report
        Allure.addAttachment("Test Status", "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        // Log test failure to Allure report
        Allure.addAttachment("Test Status", "FAILED");

        // Capture and attach the screenshot
        if (iTestResult.getThrowable() instanceof TakesScreenshot) {
            TakesScreenshot screenshotDriver = (TakesScreenshot) iTestResult.getThrowable();
            byte[] screenshotBytes = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshotBytes));
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        // Log test skipped to Allure report
        Allure.addAttachment("Test Status", "SKIPPED");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // No specific action required for this event
    }
    
    @BeforeSuite
    public void setUpBeforeSuite() {
        // Clear the allure-results folder before each run of testng.xml file
        clearAllureResultsFolder();
    }

    private void clearAllureResultsFolder() {
        // Specify the path to the allure-results folder
        String allureResultsFolderPath = "/Users/rajatrawat/Documents/appium-mobile-automation/allure-results";
        File allureResultsFolder = new File(allureResultsFolderPath);

        // Check if the folder exists and is a directory
        if (allureResultsFolder.exists() && allureResultsFolder.isDirectory()) {
            // Delete all files and subdirectories in the folder
            File[] files = allureResultsFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        } else {
            System.out.println("Allure-results folder does not exist or is not a directory.");
        }
    }
}
