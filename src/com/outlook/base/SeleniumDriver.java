package com.outlook.base;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.outlook.data.Config;

/**
 * 定义Browser基本操作, start(), close()等
 * @author zhenhaiw
 *
 */
public class SeleniumDriver 
{
	private WebDriver driver;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public SeleniumDriver()
	{
//		this.driver = driver;
//		this.initDriver();
	}
	
	public static void main(String[] args)
	{
		SeleniumDriver browser = new SeleniumDriver();
		browser.initDriver();
		browser.close();
	}
	
	/**
	 * Start WebDriver with specified browser in config XML
	 * @param browserName defined browser type, such as [firefox]/[ie32bit]/[ie64bit]/[chrome]
	 */
	public void initDriver()
	{
		try 
		{
			if(Config.browser != null)
			{
				if(Config.browser.equals("firefox"))//equals是值比较, ==是地址比较
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
					logger.error("xml配置文件的browser节点配置[" + Config.browser + "]不规范,请重新配置!");
				}
				driver.manage().window().maximize();
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				logger.info("浏览器初始化-ok!");
			}
		} catch (Exception e) 
		{
			logger.error("浏览器初始化异常: " + this.getClass().getName());;
		}
	}
	public void initSpecifiedDriver()
	{
		try 
		{
			if(Config.browser != null)
			{
				if(Config.browser.equals("specifiedFirefox"))//equals是值比较, ==是地址比较
				{
//					两种导入proxy方式都可以
//					FirefoxProfile profile = new FirefoxProfile();
//					String proxyAdr = "web-proxy.cn.hpicorp.net";
//					int proxyPort = 8080;
//					
////					profile.setPreference("network.proxy.type", 1);
//					profile.setPreference("network.proxy.http", proxyAdr);
//					profile.setPreference("network.proxy.http_port", proxyPort);
//					profile.setPreference("network.proxy.ftp", proxyAdr);
//					profile.setPreference("network.proxy.ftp_port", proxyPort);
//					profile.setPreference("network.proxy.ssl", proxyAdr);
//					profile.setPreference("network.proxy.ssl_port", proxyPort);
//					profile.setPreference("network.proxy.share_proxy_settings", true);
//
//					driver = new FirefoxDriver(profile);
					String prx="web-proxy.cn.hpicorp.net:8080";
				    Proxy proxy = new Proxy();
				    proxy.setHttpProxy(prx).setFtpProxy(prx).setSslProxy(prx);
				    DesiredCapabilities des= new DesiredCapabilities();
				    des.setCapability(CapabilityType.PROXY,proxy);

				    driver = new FirefoxDriver(des);
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
					logger.error("xml配置文件的browser节点配置[" + Config.browser + "]不规范,请重新配置!");
				}
				driver.manage().window().maximize();
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				logger.info("浏览器初始化-ok!");
			}
		} catch (Exception e) 
		{
			logger.error("浏览器初始化异常: " + this.getClass().getName());;
		}
	}
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void close()
	{
//		driver.close();
		try
		{
			Thread.sleep(5000);
			driver.quit();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			logger.error("WebDriver close 异常!");
		}
	}
}
