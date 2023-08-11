package com.algoworks.automationmobile.base_RR;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.InputStream;
import java.net.URL;

public class TestDataProvider {
	@DataProvider(name = "androidTestData")
	public static Object[][] provideAndroidTestData() {
		return provideTestData("Android", "Android");
	}

	@DataProvider(name = "iOSTestData")
	public static Object[][] provideiOSTestData() {
		return provideTestData("iOS", "iOS");
	}

	@DataProvider(name = "thirdrockTestData")
	public static Object[][] provideThirdRockTestData() {
		return provideTestData("Android", "ThirdRock");
	}

	private static Object[][] provideTestData(String platform, String sheetName) {
		try {
			
//			File file = new File("/Users/rajatrawat/Documents/appium-mobile-automation/src/main/resources/etp2.xlsx");
//			FileInputStream fis = new FileInputStream(file);
//			Workbook workbook = new XSSFWorkbook(fis);
//			Sheet sheet = workbook.getSheet(sheetName);
			
			// Load the Excel file
			String fileUrl = "https://github.com/Git-From/TestData/raw/5c00dc857b12e86b2ad66b537a33fbe77e8f0573/gitapPath.xlsx";
            URL url = new URL(fileUrl);
            InputStream is = url.openStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(sheetName);

			int rowCount = sheet.getLastRowNum();
			List<Map<String, String>> testDataList = new ArrayList<>();

			for (int i = 1; i <= rowCount; i++) {
				Row row = sheet.getRow(i);
				String executionFlag = row.getCell(getColumnIndex(sheet, "Execution")).getStringCellValue();
				String platformName = row.getCell(getColumnIndex(sheet, "Platform Name")).getStringCellValue();

				// Skip if the platform doesn't match the desired platform
				if (!platform.equalsIgnoreCase(platformName)) {
					continue;
				}

				if (executionFlag.equalsIgnoreCase("Yes")) {
					Map<String, String> testData = new HashMap<>();
					testData.put("execution", executionFlag);
					testData.put("testMethod", row.getCell(getColumnIndex(sheet, "Test Method")).getStringCellValue());
					testData.put("action", row.getCell(getColumnIndex(sheet, "Action")).getStringCellValue());
					//testData.put("elementId", row.getCell(getColumnIndex(sheet, "Element ID")).getStringCellValue());
					String combinedElementIds = row.getCell(getColumnIndex(sheet, "Element ID")).getStringCellValue();
					System.out.println("Combined Element IDs: " + combinedElementIds);
					String[] elementIds = combinedElementIds.split("\\n"); // Split by one or more whitespace characters
					System.out.println("Number of Element IDs: " + elementIds.length);
					if (elementIds.length == 4) {
						testData.put("elementId1", elementIds[0]);
						testData.put("elementId2", elementIds[1]);
						testData.put("elementId3", elementIds[2]);
						testData.put("elementId4", elementIds[3]);
					}
					else if (elementIds.length == 3) {
						testData.put("elementId1", elementIds[0]); // Skip Element ID
						testData.put("elementId2", elementIds[1]); // Continue Element ID
						testData.put("elementId3", elementIds[2]); // Heading Element ID
						testData.put("elementId4", "");
					}
					else if (elementIds.length == 2) {
						testData.put("elementId1", elementIds[0]); // Skip Element ID
						testData.put("elementId2", elementIds[1]); // Continue Element ID
						testData.put("elementId3", ""); // Heading Element ID
						testData.put("elementId4", "");
					}
					else if (elementIds.length == 1) {
						testData.put("elementId1", elementIds[0]);
						testData.put("elementId2", ""); // Set the second, third, and fourth IDs to blank
						testData.put("elementId3", "");
						testData.put("elementId4", "");
					}
					testData.put("value", row.getCell(getColumnIndex(sheet, "Value")).getStringCellValue());
					testData.put("platformName", platformName);
					testData.put("deviceName", row.getCell(getColumnIndex(sheet, "Device Name")).getStringCellValue());
					testData.put("platformVersion", row.getCell(getColumnIndex(sheet, "Platform Version")).getStringCellValue());
					testData.put("appPath", row.getCell(getColumnIndex(sheet, "App Path")).getStringCellValue());
					testData.put("automationName", row.getCell(getColumnIndex(sheet, "Automation Name")).getStringCellValue());
					testData.put("noReset", row.getCell(getColumnIndex(sheet, "No Reset")).getStringCellValue());
					testData.put("Sheet Name", sheet.toString());

					// For Android, add appPackage and appActivity to the testData
					if (sheetName.equalsIgnoreCase("Android") || sheetName.equalsIgnoreCase("ThirdRock")) {
						testData.put("appPackage", row.getCell(getColumnIndex(sheet, "App Package")).getStringCellValue());
						testData.put("appActivity", row.getCell(getColumnIndex(sheet, "App Activity")).getStringCellValue());
					}
					// For iOS, add bundleId and udid to the testData
					else if (sheetName.equalsIgnoreCase("iOS")) {
						testData.put("bundleId", row.getCell(getColumnIndex(sheet, "Bundle ID")).getStringCellValue());
						testData.put("udid", row.getCell(getColumnIndex(sheet, "UDID")).getStringCellValue());
					}

					testDataList.add(testData);
				}
			}

			// Convert the list of maps to a 2D array of Objects
			Object[][] testDataArray = new Object[testDataList.size()][];
			for (int i = 0; i < testDataList.size(); i++) {
				testDataArray[i] = new Object[]{testDataList.get(i)};
			}

			return testDataArray;
		} catch (Exception e) {
			e.printStackTrace();
			return new Object[0][0];
		}
	}


	// Helper method to get the column index based on the column name
	private static int getColumnIndex(Sheet sheet, String columnName) {
		Row headerRow = sheet.getRow(0);
		for (Cell cell : headerRow) {
			if (cell.getStringCellValue().equals(columnName)) {
				return cell.getColumnIndex();
			}
		}
		return -1;
	}
}

