package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Browser;

public class Test1 
{
	private WebDriver driver;
	Browser browser = new Browser(driver);
	
	@BeforeClass
	public void beforeClass()
	{
		System.out.println("Nothing in beforeClass.");
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		browser.start("firefox");
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
