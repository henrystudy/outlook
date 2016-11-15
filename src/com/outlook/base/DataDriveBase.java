package com.outlook.base;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.testng.annotations.DataProvider;




import com.outlook.constant.Path;

public class DataDriveBase
{
	private void initial()
	{
		if(px == null)
		{
			px = new ParseXml(Path.TESTDATA + this.getClass().getName() + ".xml");
		}
	}
	
	private void getCommonMap()
	{
		if(commonMap == null)
		{
			Element element = px.getElementObject("/*/common");
			commonMap = px.getChildNodesByElement(element);
		}
	}
	
	@DataProvider
	public Object[][] providerMethod(Method method)
	{
		this.initial();
		this.getCommonMap();
		String methodName = method.getName();
		List<Element> elements = px.getElementObjects("/*/" + methodName);
		Object[][] object = new Object[elements.size()][];
		for(int i = 0; i < elements.size(); i ++)
		{
			Map<String, String> mergeCommonMap = this.getMergeMapData(px.getChildNodesByElement(elements.get(i)), commonMap);
			Map<String, String> mergeGlobalMap = this.getMergeMapData(mergeCommonMap, GlobalInfo.global);
			Object[] temp = new Object[] {mergeGlobalMap};
			object[i] = temp;
		}
		return object;
	}
	
	private Map<String, String> getMergeMapData(Map<String, String> map1, Map<String, String> map2)
	{
		Iterator<String> it = map2.keySet().iterator();
		while(it.hasNext())
		{
			String key = it.next();
			String value = map2.get(key);
			if(!map1.containsKey(key))
			{
				map1.put(key, value);
			}
		}
		
		return map1;
	}
	
	private ParseXml px;
	private Map<String, String> commonMap;
}
