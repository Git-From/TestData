package com.algoworks.automationmobile.base_RR;

public interface AppAction {
	// Method to perform a tap action on the element identified by the elementId
	void tap(String elementId);

	// Method to perform a swipe action on the element identified by the elementId
	void swipe(String elementId, String direction);

	// Method to perform a scroll action on the element identified by the elementId
	void scroll(String elementId, String direction);

	// Method to perform a verify action on the element identified by the elementId
	void verify(String elementId, String expectedValue);

	// Method to set text on the element identified by the elementId
	void setText(String elementId, String text);

	// Method to get battery information
	String getBatteryInfo();

	// Method to check internet connectivity on the device
	boolean isInternetConnected();

	// Method to check Bluetooth status on the device
	boolean isBluetoothEnabled();

	// Method to check location services status on the device
	boolean isLocationServicesEnabled();
	// Method to check current resolution of  device
	String checkCurrentResolution();
	// Method to check if an element is present in the current view
	boolean isElementPresent(String elementId);
	// Method to get device settings
	String getSettings();
	
	boolean isAppInstalled(String bundleId, String appPath);
	void installApp(String appPath);

    void waitForAppInstallation(String bundleId);
    void launchAppDisableData();
	
}
