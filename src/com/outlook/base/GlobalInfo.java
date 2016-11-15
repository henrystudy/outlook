package com.outlook.base;

import java.util.Map;

import com.outlook.constant.Path;

public class GlobalInfo
{
	public static Map<String, String> global;
	
	static
	{
		ParseXml px = new ParseXml(Path.TESTDATA + "global.xml");
		global = px.getChildNodesByElement(px.getElementObject("/*"));
	}
}
