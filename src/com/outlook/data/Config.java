package com.outlook.data;

import com.outlook.base.ParseXml;

/**
 * 从XML配置文件读取配置信息, 例如browser, OS等等, 并赋值到静态变量
 * @author zhenhaiw
 *
 */
public class Config 
{
	public static String browser;
	public static int waitTime;
	static
	{
		ParseXml px = new ParseXml("./config/config.xml");
		browser = px.getElementText("./config/browser");
		waitTime = Integer.valueOf(px.getElementText("./config/waitTime"));
	}
	
//	public static void main(String[] args)
//	{
//		System.out.println(browser);
//		System.out.println(waitTime);
//	}
}
