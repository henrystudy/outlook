package com.outlook.testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.log.LoggingManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.outlook.base.Driver;

public class Test1 
{
	private WebDriver driver;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	Driver browser = new Driver(driver);
	
	@BeforeClass
	public void beforeClass()
	{
		System.out.println("Nothing in beforeClass.");
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		logger.info("Launching brower...");
		browser.start();
		logger.error("Browser is started successfully...");
	}
	
	@Test
	public void f() 
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	public void afterMethod()
	{
		browser.close();
	}
}
