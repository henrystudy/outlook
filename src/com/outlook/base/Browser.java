package com.outlook.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * 定义Browser基本操作, start(), close()等
 * @author zhenhaiw
 *
 */
public class Browser 
{
	private WebDriver driver;
	public Browser(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public static void main(String[] args)
	{
		WebDriver driver = null;
		Browser browser = new Browser(driver);
		browser.start("firefox");
		browser.close();
	}
	
	/**
	 * Start a browser with specified browser type
	 * @param browserName defined browser type, such as [firefox]/[ie32bit]/[ie64bit]/[chrome]
	 */
	public void start(String browserName)
	{
		try {
			if(browserName != null)
			{
				if(browserName.equals("firefox"))//equals是值比较, ==是地址比较
				{
					driver = new FirefoxDriver();
					driver.manage().window().maximize();
				}
				else if(browserName.equals("ie32bit"))
				{
					System.setProperty("webdrier.ie.driver", "./files/IEDriverServer_Win32_2.53.1/IEDriverServer.exe");
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
				}
				else if(browserName.equals("ie64bit"))
				{
					System.setProperty("webdriver.ie.driver", "./files/IEDriverServer_x64_2.53.1/IEDriverServer.exe");
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
				}
				else if(browserName.equals("chrome"))
				{
					System.setProperty("webdriver.chrome.driver", "./files/chromedriver_win32/chromedriver.exe");
					driver = new ChromeDriver();
					driver.manage().window().maximize();
				}
				else
				{
					System.out.println("No such browser name defined! "
							+ "Please input correct browser name, such as [firefox]/[ie32bit]/[ie64bit]/[chrome].");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		driver.close();
		driver.quit();
	}
}
