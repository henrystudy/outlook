package com.outlook.base;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.outlook.data.Config;

/**
 * ����Browser��������, start(), close()��
 * @author zhenhaiw
 *
 */
public class Driver 
{
	private WebDriver driver;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	public Driver(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public static void main(String[] args)
	{
		WebDriver driver = null;
		Driver browser = new Driver(driver);
		browser.start();
		browser.close();
	}
	
	/**
	 * Start WebDriver with specified browser in config XML
	 * @param browserName defined browser type, such as [firefox]/[ie32bit]/[ie64bit]/[chrome]
	 */
	public void start()
	{
		try 
		{
			if(Config.browser != null)
			{
				if(Config.browser.equals("firefox"))//equals��ֵ�Ƚ�, ==�ǵ�ַ�Ƚ�
				{
					driver = new FirefoxDriver();
				}
				else if(Config.browser.equals("ie32bit"))
				{
					System.setProperty("webdrier.ie.driver", "./files/IEDriverServer_Win32_2.53.1/IEDriverServer.exe");
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					capabilities.setCapability("ignoreProtectedModeSettings", true);
					driver = new InternetExplorerDriver();
				}
				else if(Config.browser.equals("ie64bit"))
				{
					System.setProperty("webdriver.ie.driver", "./files/IEDriverServer_x64_2.53.1/IEDriverServer.exe");
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					capabilities.setCapability("ignoreProtectedModeSettings", true);
					driver = new InternetExplorerDriver();
				}
				else if(Config.browser.equals("chrome"))
				{
					System.setProperty("webdriver.chrome.driver", "./files/chromedriver_win32/chromedriver.exe");
					driver = new ChromeDriver();
				}
				else
				{
					logger.error("xml�����ļ���browser�ڵ�����[" + Config.browser + "]���淶,����������!");
				}
				driver.manage().window().maximize();
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				logger.info("�������ʼ��-ok!");
			}
		} catch (Exception e) 
		{
			logger.error("�������ʼ���쳣: " + this.getClass().getName());;
		}
	}
	
	public void close()
	{
		driver.close();
		try
		{
			Thread.sleep(5000);
			driver.quit();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			logger.error("WebDriver close �쳣!");
		}
	}
}
