package com.outlook.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ho.yaml.Yaml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.outlook.data.Config;

public class ParseLocator 
{
	public ParseLocator(WebDriver driver, String yamlFile)
	{
		this.driver = driver;
		this.yamlFile = yamlFile;
		this.loadYaml(this.yamlFile);
	}
	
	@SuppressWarnings("unchecked")
	private void loadYaml(String yamlFile)
	{
		File file = new File("locator/" + yamlFile + ".yaml");
		if(file != null & file.exists())
		{
			try 
			{
				ml = Yaml.loadType(new FileInputStream(file.getAbsolutePath()), HashMap.class);
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}else
		{
			logger.error("Unable to find yaml file: " + yamlFile + ", please check.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadExtendYamlFile(String aYamlFile)//扩展叠加多个yaml文件并读取locator
	{
		File file = new File("locator/" + aYamlFile + ".yaml");
		if(file != null & file.exists())
		{
			try {
				extendMl = Yaml.loadType(new FileInputStream(file.getAbsolutePath()), HashMap.class);
				ml.putAll(extendMl);//从多个Yaml取locator并累加到ml
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else
		{
			logger.error("Unable to find yaml file: " + aYamlFile + ", please check.");
		}
	}
	
	private By getBy(String type, String value)
	{
		By by = null;
		if(type.equals("id"));//==比较的是引用, equals比较的是value (String a = new String("abc"); String b = new String("abc"); a==b FALSE; a.equals(b) TRUE)
		{
			by = By.id(value);
		}
		if(type.equals("name"));
		{
			by = By.name(value);
		}
		if(type.equals("className"))
		{
			by = By.className(value);
		}
		if(type.equals("linkText"))
		{
			by = By.linkText(value);
		}
		if(type.equals("xpath"))
		{
			by = By.xpath(value);
		}
		return by;
	}
	
	private WebElement getLocator(String key, boolean wait, String[] replace)
	{
		WebElement element = null;
		if(ml.containsKey(key))
		{
			String type = ml.get(key).get("type");
			String value = ml.get(key).get("value");
			if(replace != null)
				value = this.getReplacedString(value, replace);
			By by = this.getBy(type, value);
			if(wait)
			{
				element = this.waitForElement(by);
				boolean flag = this.isWaitElementToBeDisplayed(element);
				if(!flag)
					element = null;
			}else
			{
				try
				{
					element = driver.findElement(by);
				} catch (Exception e)
				{
					element = null;
				}
			}
		}else
		{
			logger.error("Unable to find Locator [" + key + "] in yamlFile" + ".yaml");
		}
			
		return element;
	}
	
	public WebElement getElement(String key)
	{
		return this.getLocator(key, true, null);
	}
	
	public WebElement getElement(String key, String[] replace)
	{
		return this.getLocator(key, true, replace);
	}
	
	public WebElement getElementNoWait(String key)
	{
		return this.getLocator(key, false, null);
	}
	
	public WebElement getElementNoWait(String key, String[] replace)
	{
		return this.getLocator(key, false, replace);
	}
	
	private WebElement waitForElement(final By by)
	{
		WebElement element = null;
		int waitTime = Config.waitTime;
		try
		{
			element = new WebDriverWait(driver, waitTime).until(new ExpectedCondition<WebElement>()
			{
				public WebElement apply(WebDriver d)
				{
					return d.findElement(by);
				}
			});
		} catch (Exception e)
		{
			logger.error("Unable to catch " + by.toString() + "until" + waitTime + ".");
		}
		
		return element;
	}
	
	private boolean isWaitElementToBeDisplayed(final WebElement element)
	{
		boolean wait = false;
		if(element == null)
			return wait;
		int waitTime = Config.waitTime;
		wait = new WebDriverWait(driver, waitTime).until(new ExpectedCondition<Boolean>()
		{
			public Boolean apply(WebDriver d)
			{
				return element.isDisplayed();
			}
		});
		
		return wait;
	}
	
	/**
	 * 暂时没用到
	 * @param element
	 * @return
	private boolean waitForElementToBeDisappeared(final WebElement element)
	{
		boolean wait = false;
		if(element == null)
			return wait;
		int waitTime = Config.waitTime;
		wait = new WebDriverWait(driver, waitTime).until(new ExpectedCondition<Boolean>()
		{
			public Boolean apply(WebDriver d)
			{
				return !element.isDisplayed();
			}
		});
		
		return wait;
	}
	 */
	
	//参数化
	private String getReplacedString(String aStr, String[] bStr)
	{
		String tag = "%s";
		//判断aStr是不是tag字符串的重复叠加
		boolean flag = 	this.validateStringRepeat(aStr, tag);
		if(flag)
		{
			for(String str : bStr)
			{
				aStr = aStr.replace(tag, str);
			}
		}else
		{
			logger.error("Parameter [" + tag + "] does NOT exist, please check yaml file.");
		}
		
		return aStr;
	}
	
	public void replaceLocatorValue(String aValue, String bValue)
	{
		Set<String> keys = ml.keySet();
		for(String key : keys)
		{
			if(ml.get(key).get("value").contains("%" + aValue + "%"))
			{
				System.out.println(ml.get(key).get("value"));
				//动态传值
				//把特定的字符串替换掉,得到替换部分字符的value,否则会整个value替换掉, 比如实现替换xpath中一段%%标记的word
				String temp = ml.get(key).get("value").replaceAll("%" + aValue + "%", bValue);
				ml.get(key).put("value", temp);
				System.out.println(ml.get(key).get("value"));
			}
		}
	}
	
	private boolean validateStringRepeat(String aStr, String tag)
	{
		boolean flag = false;
		//判断value是不是tag字符的重复填充
		if(aStr.length()%tag.length() == 0)
		{
			for(int i = 0; i < aStr.length(); i = i + tag.length())
			{
				//注意String.substring(beginIndex,endIndex), return the substring begins at beginIndex and extends to (endIndex - 1)
				if(aStr.substring(i, i + tag.length()).equals(tag))
				{
					flag = true;
				}else
				{
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	public static void main(String[] args)
	{
		SeleniumDriver sdriver = new SeleniumDriver();
		sdriver.initSpecifiedDriver();;
		WebDriver driver = sdriver.getDriver();
		ParseLocator pl = new ParseLocator(driver, "outlook");
		
		driver.navigate().to("http://www.outlook.com");
		try
		{
			Thread.sleep(300);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement usr = pl.getElement("outlook_login_usr");
		usr.click();
		usr.sendKeys("srvttest01@outlook.com");
		WebElement pwd = pl.getElement("outlook_login_pwd");
		pwd.click();
		pwd.sendKeys("srvttest@123");
//		pl.getElement("outlook_login_loginButton").click();
//		sdriver.close();
////		pl.loadExtendYamlFile("outlook1");
////		pl.replaceLocatorValue("productid", "xxxxx");
//		String[] replace = {"11111-"};
////		pl.getElementNoWait("baidu_button1", replace);
//		String xStr = pl.ml.get("baidu_button6").get("value");
//		System.out.println(xStr);
//		xStr = pl.getReplacedString(xStr, replace);
//		System.out.println(xStr);
	}
	
	private WebDriver driver;
	private String yamlFile;
	private Map<String, Map<String, String>> ml;
	private Map<String, Map<String, String>> extendMl;
	private static Logger logger = LogManager.getLogger(ParseLocator.class.getName());
}
