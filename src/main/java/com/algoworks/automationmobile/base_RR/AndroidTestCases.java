package com.algoworks.automationmobile.base_RR;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class AndroidTestCases extends BaseClass  {

	/*
	 * @BeforeClass public void setUp() throws Exception { // Read the test data from Excel Object[][] testData =
	 * TestDataProvider.provideAndroidTestData(); // Set up the driver based on the platform from the test data for
	 * (Object[] data : testData) { Map<String, String> testDataMap = (Map<String, String>) data[0]; String platformName
	 * = testDataMap.get("platformName"); String deviceName = testDataMap.get("deviceName"); String platformVersion =
	 * testDataMap.get("platformVersion"); String appPath = testDataMap.get("appPath"); String appPackage =
	 * testDataMap.get("appPackage"); String appActivity = testDataMap.get("appActivity"); String automationName =
	 * testDataMap.get("automationName"); String noReset = testDataMap.get("noReset");
	 * setUpAndroidAppiumDriver(platformName, deviceName, platformVersion, appPath, appPackage, appActivity,
	 * automationName, noReset); } }
	 */
	
	@BeforeClass
    public void setUpAndroidDriver() throws Exception {
        // Read the test data from Excel using TestDataProvider
        Object[][] testData = TestDataProvider.provideAndroidTestData();
        // Set up the Appium driver for Android based on the platform from the test data
        for (Object[] data : testData) {
            Map<String, String> testDataMap = (Map<String, String>) data[0];
            setUpAppiumDriver(testDataMap);
        }
    }

    @Test(dataProvider = "androidTestData", dataProviderClass = TestDataProvider.class)
    public void testAndroidUserActions(Map<String, String> testData) throws InterruptedException {
        String executionFlag = testData.get("execution");
        String testMethod = testData.get("testMethod");
        String action = testData.get("action");
        String elementId = testData.get("elementId1");
        String value = testData.get("value");

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

        AndroidAppAction appAction = new AndroidAppAction(getAndroidDriver());
        // Perform the action based on the test data
        switch (action) {
            case "TAP":
                appAction.tap(elementId);
                break;
            case "SWIPE":
            	appAction.swipe(elementId,value);
                break;
            case "SCROLL":
            	appAction.scroll(elementId, "DOWN");
                break;
            case "VERIFY":
            	appAction.verify(elementId, value);
                break;
            case "SET TEXT":
            	appAction.setText(elementId,value);
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
                System.out.println("Element Present: " + isElementPresent);
                break;
            default:
                // Handle the case when the action does not match any known actions
                System.out.println("Unknown action: " + action);
                break;  
        }
    }
    
    
    // Rest of the test methods...
}
