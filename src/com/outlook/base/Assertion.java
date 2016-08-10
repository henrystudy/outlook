package com.outlook.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * import testNG, not jUnit
 * @author zhenhaiw
 *
 */
public class Assertion 
{
	public static void verifyEquals(Object actual, Object expected)
	{
		try 
		{
			Assert.assertEquals(actual, expected);
		} catch (Exception e) 
		{
			flag = false;
			logger.error("Assertion fail, logging...");
			//Take screenshot if failure
		}
	}
	
	public static void verifyEquals(Object actual, Object expected, String message)
	{
		try 
		{
			Assert.assertEquals(actual, expected, message);
		} catch (Exception e) 
		{
			flag = false;
			logger.error("Assertion fail, logging...");
			//Take screenshot if failure
		}
	}
	
	public static void verifyEquals(Object actual, Object expected, WebDriver driver)
	{
		try 
		{
			Assert.assertEquals(actual, expected);
		} catch (Exception e) 
		{
			flag = false;
			logger.error("Assertion fail, logging...");
			Screenshot.takeScreenshot((TakesScreenshot)driver, "./test-output/screenshot");
		}
	}
	
	public static void verifyEquals(Object actual, Object expected, String message, WebDriver driver)
	{
		try 
		{
			Assert.assertEquals(actual, expected, message);
		} catch (Exception e) 
		{
			flag = false;
			logger.error("Assertion fail, logging...");
			Screenshot.takeScreenshot((TakesScreenshot)driver, "./test-output/screenshot");
		}
	}
	
	private static boolean flag = true;
	private static Logger logger = LogManager.getLogger(Assertion.class.getName());
}
