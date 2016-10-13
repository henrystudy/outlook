package com.outlook.data;

import com.outlook.base.ParseXml;

/**
 * ��XML�����ļ���ȡ������Ϣ, ����browser, OS�ȵ�, ����ֵ����̬����
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
