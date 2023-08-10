package com.algoworks.automationmobile.base_RR;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileBy.ByAccessibilityId;
import io.appium.java_client.MobileElement;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class IOSAppAction implements AppAction {

    private AppiumDriver<MobileElement> iosDriver;

    public IOSAppAction(AppiumDriver<MobileElement> iosDriver) {
        this.iosDriver = iosDriver;
    }

    @Override
    public void tap(String elementId) {
        MobileElement element = findElementByIdOrAccessibilityId(elementId);
        element.click();
        sleep(5000);
    }

    @Override
    public void swipe(String elementId, String direction) {
        // Implement swipe for iOS if needed
    }

    @Override
    public void scroll(String elementId, String direction) {
        // Implement scroll for iOS if needed
    }

    @Override
    public void verify(String elementId, String expectedValue) {
        MobileElement element = findElementByIdOrAccessibilityId(elementId);
        String actualValue = element.getText();

        // Compare the actual value with the expected value
        if (actualValue.equals(expectedValue)) {
            System.out.println("Verification Passed! Actual Value: " + actualValue + ", Expected Value: " + expectedValue);
        } else {
            System.out.println("Verification Failed! Actual Value: " + actualValue + ", Expected Value: " + expectedValue);
        }

        sleep(5000);
    }

    @Override
    public void setText(String elementId, String text) {
        MobileElement element = findElementByIdOrAccessibilityId(elementId);
        element.clear();
        element.sendKeys(text);
        sleep(1000);
    }

    @Override
    public String getBatteryInfo() {
        // Implement getting battery info for iOS if needed
        return "Battery Info not available for iOS";
    }

    @Override
    public boolean isInternetConnected() {
        // Implement checking internet connection for iOS if needed
        return true;
    }

    @Override
    public boolean isBluetoothEnabled() {
        // Implement checking Bluetooth status for iOS if needed
        return true;
    }

    @Override
    public boolean isLocationServicesEnabled() {
        // Implement checking location services status for iOS if needed
        return true;
    }

    @Override
    public String checkCurrentResolution() {
        // Implement getting screen resolution for iOS if needed
        return "Resolution not available for iOS";
    }

    @Override
    public boolean isElementPresent(String elementId) {
        return findElementByIdOrAccessibilityId(elementId) != null;
    }

    // Helper method to find the element by ID or AccessibilityId
    private MobileElement findElementByIdOrAccessibilityId(String elementId) {
        try {
            // Find element by ID
            return iosDriver.findElement(By.id(elementId));
        } catch (WebDriverException e) {
            // If element not found by ID, try finding by AccessibilityId
            return iosDriver.findElement(ByAccessibilityId.id(elementId));
        }
    }

    // Helper method for sleep
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	@Override
	public String getSettings()
	{
		Map<String, Object> settings = iosDriver.getSettings();
		return settings.toString();
	}

	 @Override
	    public boolean isAppInstalled(String bundleId, String appPath) {
	        // Check if the app is installed
	        boolean isInstalled = iosDriver.isAppInstalled(bundleId);
	        
	        // If the app is not installed, try to install it
	        if (!isInstalled) {
	            installApp(appPath); // Replace with the actual app file path
	            // Wait for the app installation to complete
	            waitForAppInstallation(bundleId);
	            // Check if the app is installed after the installation
	            isInstalled = iosDriver.isAppInstalled(bundleId);
	        }
	        
	        return isInstalled;
	    }

	 @Override
	    public void installApp(String appPath) {
	        // Use the iosDriver to install the app using the given appPath
	        try {
	            iosDriver.installApp(appPath);
	        } catch (Exception e) {
	            // Handle any exceptions that may occur during app installation
	            e.printStackTrace();
	        }
	    }

	 @Override
	    public void waitForAppInstallation(String bundleId) {
	        // Add logic to wait for the app installation to complete
	        // You can use WebDriverWait or Thread.sleep() to wait for a few seconds
	        // after installing the app before checking if it is installed
	        
	        // Example using WebDriverWait:
	        WebDriverWait wait = new WebDriverWait(iosDriver, 60); // Wait for 60 seconds
	        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(bundleId)));
	    }

	@Override
	public void launchAppDisableData()
	{
		// TODO Auto-generated method stub
		
	}
}

