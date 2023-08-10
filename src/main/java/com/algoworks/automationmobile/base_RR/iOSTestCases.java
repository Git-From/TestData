package com.algoworks.automationmobile.base_RR;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;

public class iOSTestCases extends BaseClass {

	@BeforeClass
    public void setUp() throws Exception {
        // Read the test data from Excel
        Object[][] testData = TestDataProvider.provideiOSTestData();
        // Set up the driver based on the platform from the test data
        for (Object[] data : testData) {
            Map<String, String> testDataMap = (Map<String, String>) data[0];
            String platformName = testDataMap.get("platformName");
            String deviceName = testDataMap.get("deviceName");
            String platformVersion = testDataMap.get("platformVersion");
            String appPath = testDataMap.get("appPath");
            String automationName = testDataMap.get("automationName");
            String noReset = testDataMap.get("noReset");
            String bundleId = testDataMap.get("bundleId");
            String udid = testDataMap.get("udid");
            setUpIOSAppiumDriver(platformName, deviceName, platformVersion, appPath, automationName, noReset, bundleId, udid);
        }
    }	



@Test(dataProvider = "iOSTestData", dataProviderClass = TestDataProvider.class)
public void testIosUserActions(Map<String, String> testData) throws InterruptedException {
	String executionFlag = testData.get("execution");
	String testMethod = testData.get("testMethod");
	String action = testData.get("action");
	String elementId = testData.get("elementId");
	String value = testData.get("value");
	String bundleId= testData.get("bundleId");
	String appPath = testData.get("appPath");

	// Check if the executionFlag is null or empty
	if (executionFlag == null || executionFlag.isEmpty()) {
		System.out.println("Skipping test with action: " + action + " and element ID: " + elementId);
		return;
	}

	// Check if the test should be executed based on the execution flag
	if (!executionFlag.equalsIgnoreCase("Yes")) {
		System.out.println("Skipping test with action: " + action + " and element ID: " + elementId);
		return;
	}

	// Proceed with the test logic only if the execution flag is "Y"
	System.out.println("Executing test: " + testMethod + " with action: " + action + " on element " + elementId);

	IOSAppAction appAction = new IOSAppAction(getIOSDriver());
	// Perform the action based on the test data
	switch (action) {
		case "TAP":
			appAction.tap(elementId);
			break;
		case "SWIPE":
			appAction.swipe(elementId, "RIGHT");
			break;
		case "SCROLL":
			appAction.scroll(elementId, "DOWN");
			break;
		case "VERIFY":
			appAction.verify(elementId, value);
			break;
		case "SET TEXT":
			appAction.setText(elementId, value);
			break;
		case "GET BATTERY INFO":
			String batteryInfo = appAction.getBatteryInfo();
			System.out.println(batteryInfo);
			break;
		case "CHECK INTERNET":
			boolean isInternetConnected = appAction.isInternetConnected();
			System.out.println("Internet Connected: " + isInternetConnected);
			break;
		case "CHECK BLUETOOTH":
			boolean isBluetoothEnabled = appAction.isBluetoothEnabled();
			System.out.println("Bluetooth Enabled: " + isBluetoothEnabled);
			break;
		case "CHECK LOCATION SERVICES":
			boolean isLocationServicesEnabled = appAction.isLocationServicesEnabled();
			System.out.println("Location Services Enabled: " + isLocationServicesEnabled);
			break;
		case "GET SCREEN RESOLUTION":
			String screenResolution = appAction.checkCurrentResolution();
			System.out.println(screenResolution);
			break;
		case "IS ELEMENT PRESENT":
			boolean isElementPresent = appAction.isElementPresent(elementId);
			System.out.println("Bluetooth Enabled: " + isElementPresent);
			break;
		case "GET SETTINGS":
			String settings = appAction.getSettings();
			System.out.println(settings);
			break;
		case "IS APP INSTALLED":
			boolean isAppInstalled = appAction.isAppInstalled(bundleId,appPath);
			System.out.println("Is App Installed: " + isAppInstalled);
			break;
		default:
			// Handle the case when the action does not match any known actions
			System.out.println("Unknown action: " + action);
			break;
	}
}


// Rest of the test methods...
}
