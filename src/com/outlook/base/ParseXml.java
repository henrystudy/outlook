package com.outlook.base;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * .jar needed for parse XML
 * dom4j - parse xml
 * jaxen - recognize xpath expression
 * @author zhenhaiw
 *
 */

public class ParseXml 
{
	public ParseXml(String filePath)
	{
		this.filePath = filePath;
		this.load(this.filePath);
	}
	
	public void load(String filePath)
	{
		File file = new File(filePath);
		
		if(file.exists())
		{
			SAXReader reader = new SAXReader();
			try 
			{
				document = reader.read(file);
			} catch (DocumentException e) 
			{
				logger.error("Loading file error: " + filePath);
			}
		}
		else
		{
			logger.error("File does NOT exist: " + filePath);
		}
	}
	
	public Element getElementObject(String elementPath)
	{
//		Element rootElm = document.getRootElement();
//		return rootElm.element(elementPath);
		return (Element)document.selectSingleNode(elementPath);
	}
	
	public List<Element> getElementObjects(String elementPath)
	{
//		Element rootElm = document.getRootElement();
//		return rootElm.elements(elementPath);
		return (List)document.selectNodes(elementPath);
	}
	
	public String getElementText(String elementPath)
	{
		Element element = this.getElementObject(elementPath);
		if(element != null)
		{
			return element.getTextTrim();
		}
		else
		{
			return null;
		}
	}
	
	public boolean isExist(String elementPath)
	{
		boolean flag = false;
		if(this.getElementObject(elementPath) != null)//不要用equals(null), null时会返回空指针异常NullPointerException()
		{
			flag = true;
		}
		else
		{
			logger.error("Element does NOT exist: " + elementPath);
		}
		return flag;
	}
	
	public Map<String, String> getChildNodesByElement(Element element)
	{
		Map<String,String> map = new HashMap<String, String>();
		List<Element> child = element.elements();
		for(Element e : child)
		{
			map.put(e.getName(), e.getText());
		}
		return map;
	}
	
	public Object[][] getAllChild(String elementPath)
	{
		List<Element> elements = this.getElementObjects(elementPath);
		Object[][] object = new Object[elements.size()][];
		for(int i = 0; i < elements.size(); i ++)
		{
			object[i] = new Object[] {this.getChildNodesByElement(elements.get(i))};
		}
		return object;
	}
	
	private String filePath;
	private Document document;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public static void main(String[] args)
	{
		ParseXml px = new ParseXml("./config/TestBaidu.xml");
		System.out.println(px.isExist("/*/value"));
		System.out.println(px.getElementText("/*/value"));
		
		Object o = px.getAllChild("/*/testUI")[1][1];
		System.out.println(((Map<String,String>)o).get("description"));
	}
}
