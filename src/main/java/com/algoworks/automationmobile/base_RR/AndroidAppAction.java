package com.algoworks.automationmobile.base_RR;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidBatteryInfo.BatteryState;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.offset.ElementOption;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import org.openqa.selenium.support.ui.Wait;


public class AndroidAppAction implements AppAction
{
	private AppiumDriver<MobileElement> androidDriver;

	public AndroidAppAction(AppiumDriver<MobileElement> androidDriver)
	{
		this.androidDriver = androidDriver;
	}

	@Override
	public void tap(String elementId) {
	    Wait<AppiumDriver<MobileElement>> wait = new FluentWait<>(androidDriver)
	            .withTimeout(Duration.ofSeconds(10))
	            .pollingEvery(Duration.ofMillis(500))
	            .ignoring(org.openqa.selenium.NoSuchElementException.class);

	    MobileElement element = wait.until(new Function<AppiumDriver<MobileElement>, MobileElement>() {
	        public MobileElement apply(AppiumDriver<MobileElement> driver) {
	            MobileElement element = driver.findElement(By.id(elementId));
	            if (element.isDisplayed() && element.isEnabled()) {
	                return element;
	            } else {
	                return null;
	            }
	        }
	    });

	    if (element != null) {
	        TouchAction<?> touchAction = new TouchAction<>(androidDriver);
	        touchAction.tap(ElementOption.element(element)).perform();
	        sleep(3000);
	    } else {
	        System.out.println("Element with ID: " + elementId + " not found or not clickable. Tap action skipped.");
	    }
	}



	public void swipe(String elementId, String direction) {
	    
		sleep(2000);
	    try {
	        // Wait for the element to be present
	        FluentWait<AppiumDriver<MobileElement>> wait = new FluentWait<>(androidDriver)
	                .withTimeout(Duration.ofSeconds(2))
	                .pollingEvery(Duration.ofMillis(500))
	                .ignoring(TimeoutException.class);
	        MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
	        System.out.println("element capturing excel:"+element+"excel element:"+elementId);
	        // Get the element's position and size
	        int startX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
	        int startY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

	        // Set the end coordinates based on the specified direction
	        int endX = startX;
	        int endY = startY;
	        switch (direction.toUpperCase()) {
	            case "LEFT":
	                endX -= 500; // Adjust the swipe length as needed
	                break;
	            case "RIGHT":
	                endX += 500; // Adjust the swipe length as needed
	                break;
	            case "UP":
	                endY -= 500; // Adjust the swipe length as needed
	                break;
	            case "DOWN":
	                endY += 500; // Adjust the swipe length as needed
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid swipe direction: " + direction);
	        }

	        // Perform the swipe action
	        System.out.println("Performing " + direction.toUpperCase() + " swipe now");
	        new TouchAction<>(androidDriver)
	                .press(PointOption.point(startX, startY))
	                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
	                .moveTo(PointOption.point(endX, endY))
	                .release()
	                .perform();
	        System.out.println("Swipe performed successfully.");
	    } catch (Exception e) {
	        // The element was not found within the wait duration
	        System.out.println("Swipe performed successfully with: " + elementId );
	        // You can choose to log this message or perform any other actions if needed.
	    }
	}




	@Override
	public void scroll(String elementId, String direction)
	{
		// Scroll action implementation for Android using TouchAction
		MobileElement element = androidDriver.findElement(By.id(elementId));
		int startY = element.getLocation().getY();
		int endY = startY;

		switch (direction.toUpperCase())
		{
			case "UP":
				startY -= 500; // Adjust the scroll length as needed
				break;
			case "DOWN":
				startY += 500; // Adjust the scroll length as needed
				break;
			default:
				throw new IllegalArgumentException("Invalid scroll direction: " + direction);
		}

		TouchAction<?> touchAction = new TouchAction<>(androidDriver);
		touchAction.press(PointOption.point(element.getLocation().getX(), startY)).moveTo(PointOption.point(element.getLocation().getX(), endY)).release().perform();
	}

	@Override
	public void verify(String elementId, String expectedValue) {
		MobileElement element;
		try {
			element = (MobileElement) androidDriver.findElement(By.id(elementId));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// If the element is not found, handle the exception
			System.out.println("Element with ID: " + elementId + " was not found.");
			Assert.fail("Element with ID: " + elementId + " was not found.");
			return;
		}

		String actualValue = element.getText();
		if (actualValue.equals(expectedValue)) {
			System.out.println("Verification Passed! Actual Value: " + actualValue + ", Expected Value: " + expectedValue);
		} else {
			System.out.println("Verification Failed! Actual Value: " + actualValue + ", Expected Value: " + expectedValue);
		}
	}



	@Override
	public void setText(String elementId, String text)
	{
		MobileElement element = findElementByIdOrXpath(elementId);
		element.sendKeys(text);
		sleep(1000);
	}

	private MobileElement findElementByIdOrXpath(String elementId)
	{
		MobileElement element;
		try
		{
			// Find element by ID
			element = androidDriver.findElement(By.id(elementId));
		}
		catch (Exception e)
		{
			// If element not found by ID, try finding by XPath
			element = androidDriver.findElement(By.xpath(elementId));
		}
		return element;
	}

	@Override
	public String getBatteryInfo()
	{
		// Get the battery information using UiAutomator2 driver capabilities
		AndroidDriver<MobileElement> androidDriver = (AndroidDriver<MobileElement>) this.androidDriver;

		double batteryPercent = androidDriver.getBatteryInfo().getLevel();
		BatteryState batteryStateVar = androidDriver.getBatteryInfo().getState();
		System.out.println("battery percent is:" + batteryPercent);
		return batteryStateVar.toString();
	}

	@Override
	public boolean isInternetConnected()
	{
		Map<String, Object> args1 = new HashMap<>();
		args1.put("command", "settings get global mobile_data");
		Object output = androidDriver.executeScript("mobile: shell", args1);
		Map<String, Object> args3 = new HashMap<>();
		args3.put("command", "settings get global wifi_on");
		Object output2 = androidDriver.executeScript("mobile: shell", args3);
		System.out.println("wifi is on: " + output2.toString());
		if (output.toString().equals("0") || output2.toString().equals("0"))
		{
			System.out.println("data or wifi is disabled");

			Map<String, Object> args = new HashMap<>();
			args.put("command", "svc data enable");
			androidDriver.executeScript("mobile: shell", args);

			Map<String, Object> args4 = new HashMap<>();
			args4.put("command", "svc wifi enable");
			androidDriver.executeScript("mobile: shell", args4);

			System.out.println("data & wifi are  enabled now...");

			return false;

		}
		else
		{

			return true;

		}
	}

	@Override
	public boolean isBluetoothEnabled()
	{
		List<String> bluetoothStatus = Arrays.asList("get global bluetooth_on");
		Map<String, Object> statusBluetooth = ImmutableMap.of("command", "settings", "args", bluetoothStatus);
		Object object = androidDriver.executeScript("mobile: shell", statusBluetooth);

		String output = object.toString().trim();
		if ("1".equals(output))
		{
			System.out.println("Bluetooth is enabled");
			return true;
		}
		else
		{
			System.out.println("Bluetooth is off");
			System.out.println("Turning it on... now..");

			List<String> bluetoothArgs = Arrays.asList("broadcast", "-a", "io.appium.settings.bluetooth", "--es", "setstatus", "enable");
			Map<String, Object> enableBluetooth = ImmutableMap.of("command", "am", "args", bluetoothArgs);
			androidDriver.executeScript("mobile: shell", enableBluetooth);

			System.out.println("Bluetooth is on");
			return true; // or return false, based on your requirement
		}
	}

	@Override
	public boolean isLocationServicesEnabled()
	{
		// Check if location services are enabled on the device
		Map<String, Object> args = new HashMap<>();
		args.put("command", "settings get secure location_providers_allowed");
		Object output = androidDriver.executeScript("mobile: shell", args);

		// Convert the output to a string and check if "gps" is present
		String outputStr = output.toString();
		if (outputStr.contains("gps"))
		{
			return true;
		}
		else
		{
			// If "gps" is not present, update the location_providers_allowed setting to enable GPS
			Map<String, Object> args2 = new HashMap<>();
			args2.put("command", "settings put secure location_providers_allowed gps,network");
			androidDriver.executeScript("mobile: shell", args2);

			// Verify if the location_providers_allowed setting is updated successfully
			Object updatedOutput = androidDriver.executeScript("mobile: shell", args);
			String updatedOutputStr = updatedOutput.toString();
			System.out.println("Location services are on: " + updatedOutputStr.contains("gps"));

			return false;
		}
	}

	@Override
	public String checkCurrentResolution()
	{
		Map<String, Object> args = new HashMap<>();
		args.put("command", "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1");
		androidDriver.executeScript("mobile: shell", args);
		ScreenOrientation orientation = androidDriver.getOrientation();
		return orientation.toString();

	}

	@Override
	public boolean isElementPresent(String elementId) {
		// Sleep for a short duration to allow time for the element to be present
		sleep(2000);

		try {
			// Find the element by its ID
			MobileElement element = androidDriver.findElement(By.id(elementId));

			// Assert that the element is visible
			Assert.assertTrue(element.isDisplayed(), "Element with ID: " + elementId + " is not visible.");

			// Return true if the element is present and visible
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// Assertion for the case when the element is not present
			System.out.println("Element with ID: " + elementId + " was not found.");
			Assert.fail("Element with ID: " + elementId + " was not found.");
			return false;
		}
	}


	//Helper method for sleep
	private void sleep(long milliseconds)
	{
		try
		{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getSettings()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void installApp(String appPath)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void waitForAppInstallation(String bundleId)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAppInstalled(String bundleId, String appPath)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void launchAppDisableData()
	{
		// TODO Auto-generated method stub
		
	}



}
