package com.algoworks.automationmobile.base_RR;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

public class ThridRockTestCases extends BaseClass
{
	// Create an instance of ThirdRockAppAction using the Android driver
	private ThirdRockAppAction appAction;
	

	@BeforeClass
	public void setUp() throws Exception
	{
		// Read the test data from Excel
		Object[][] testData = TestDataProvider.provideThirdRockTestData();
		// Set up the driver based on the platform from the test data
		for (Object[] data : testData)
		{
			Map<String, String> testDataMap = (Map<String, String>) data[0];
			String platformName = testDataMap.get("platformName");
			String deviceName = testDataMap.get("deviceName");
			String platformVersion = testDataMap.get("platformVersion");
			String appPath = testDataMap.get("appPath");
			String appPackage = testDataMap.get("appPackage");
			String appActivity = testDataMap.get("appActivity");
			String automationName = testDataMap.get("automationName");
			String noReset = testDataMap.get("noReset");

			// Call the method to set up the Appium driver
			setUpAndroidAppiumDriver(platformName, deviceName, platformVersion, appPath, appPackage, appActivity, automationName, noReset);
		}
		// Create an instance of ThirdRockAppAction using the Android driver
		appAction = new ThirdRockAppAction(getAndroidDriver());
	}

	@Test(dataProvider = "thirdrockTestData", dataProviderClass = TestDataProvider.class)
	public void testAndroidUserActions1(Map<String, String> testData) throws InterruptedException
	{
		String executionFlag = testData.get("execution");
		String testMethod = testData.get("testMethod");
		String action = testData.get("action");

		// Check if the executionFlag is null or empty
		if (executionFlag == null || executionFlag.isEmpty())
		{
			System.out.println("Skipping test with action: " + action);
			return;
		}

		// Check if the test should be executed based on the execution flag
		if (!executionFlag.equalsIgnoreCase("Yes"))
		{
			System.out.println("Skipping test with action: " + action);
			return;
		}

		// Proceed with the test logic only if the execution flag is "Y"
		System.out.println("Executing test: " + testMethod + " with action: " + action);

		appAction.launchApp();// Assuming this is a common step for all test cases

		// Perform the action based on the test data
		switch (action)
		{
			case "LaunchAppDisableData":
				appAction.launchAppDisableData();
				break;
			case "IsAppInstalled":
				appAction.isAppInstalled();
				break;
			case "LaunchAppDisableWifi":
				appAction.launchAppDisableWifi();
				break;
			case "IsAppRemoved":
				appAction.isAppRemoved();
				break;
			case "VerifyScreenContent":
				appAction.verifyScreenContent(testData);
				break;
			case "ClickContinueButton":
				appAction.clickContinueButton(testData);
				break;
			case "NavigateToFirstScreen":
				appAction.navigateToFirstScreen(testData);
				break;
			case "ClickSkipButton":
				appAction.clickSkipButton(testData);
				break;
			default:
				// Handle the case when the action does not match any known actions
				System.out.println("Unknown action: " + action);
				return;
		}

	}

}
