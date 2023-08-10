package com.algoworks.automationmobile.base_RR;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseClass
{

	private static BaseClass instance;
	private AppiumDriver<MobileElement> androidDriver;
	private AppiumDriver<MobileElement> iosDriver;
	private static boolean isSessionStarted = false;

	// Public method to get the instance of BaseClass (Singleton)
	public static BaseClass getInstance()
	{
		if (instance == null)
		{
			instance = new BaseClass();
		}
		return instance;
	}

	// Set up the Appium driver based on the platform for Android
	protected void setUpAndroidAppiumDriver(String platformName, String deviceName, String platformVersion, String appPath, String appPackage, String appActivity, String automationName, String noReset)
	{
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
		desiredCapabilities.setCapability("app", appPath);
		desiredCapabilities.setCapability("appPackage", appPackage);
		desiredCapabilities.setCapability("appActivity", appActivity);
		desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, noReset);

		try
		{
			androidDriver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
			androidDriver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS); // Adjust the timeout value as needed
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	// Set up the Appium driver based on the platform for iOS
	protected void setUpIOSAppiumDriver(String platformName, String deviceName, String platformVersion, String appPath, String automationName, String noReset, String bundleId, String udid)
	{
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
		desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, noReset);
		desiredCapabilities.setCapability("bundleId", bundleId);
		desiredCapabilities.setCapability("app", appPath);
		desiredCapabilities.setCapability("udid", udid);

		try
		{
			iosDriver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	// Get the Android driver
	protected AppiumDriver<MobileElement> getAndroidDriver()
	{
		return androidDriver;
	}

	// Get the iOS driver
	protected AppiumDriver<MobileElement> getIOSDriver()
	{
		return iosDriver;
	}

	// Public method to set up the driver based on the platform (accessible from child classes)
	public void setUpAppiumDriver(Map<String, String> testData)
	{
		// Check if the session is already started
		if (!isSessionStarted)
		{
			String platformName = testData.get("platformName");
			String deviceName = testData.get("deviceName");
			String platformVersion = testData.get("platformVersion");
			String appPath = testData.get("appPath");
			String appPackage = testData.get("appPackage");
			String appActivity = testData.get("appActivity");
			String automationName = testData.get("automationName");
			String noReset = testData.get("noReset");
			String bundleId = testData.get("bundleId");
			String udid = testData.get("udid");
			String sheetName = testData.get("Sheet Name");

			if (platformName.equalsIgnoreCase("Android") || sheetName.equalsIgnoreCase("ThirdRock"))
			{
				setUpAndroidAppiumDriver(platformName, deviceName, platformVersion, appPath, appPackage, appActivity, automationName, noReset);
			}
			else if (platformName.equalsIgnoreCase("iOS"))
			{
				setUpIOSAppiumDriver(platformName, deviceName, platformVersion, appPath, automationName, noReset, bundleId, udid);
			}
			else
			{
				throw new IllegalArgumentException("Unsupported platform: " + platformName);
			}

			// Set the flag to indicate that the session is started
			isSessionStarted = true;
		}
	}

	@AfterTest(alwaysRun = true)
	public void quitAppiumDrivers()
	{
		// Close the drivers and release resources
		if (androidDriver != null)
		{
			androidDriver.quit();
		}
		if (iosDriver != null)
		{
			iosDriver.quit();
		}
	}
}
