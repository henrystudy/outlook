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
	static
	{
		ParseXml px = new ParseXml("./config/config.xml");
		browser = px.getElementText("./config/browser");
	}
}
