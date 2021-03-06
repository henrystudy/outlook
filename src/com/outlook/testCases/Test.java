package com.outlook.testCases;

import java.io.File;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.outlook.constant.Path;

public class Test
{
	public static void main(String[] args) 
	{
//		Log4jTest log4j = new Log4jTest();
//		log4j.log4jTest();
		System.out.println(System.getProperty("user.dir"));
		System.out.println((new File(Path.CAPTURESCREENSHOTPATH)).exists());
	}
}

class Log4jTest
{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public void log4jTest()
	{
//		for(int i = 0; i < 10000; i ++)
//		{
//			System.out.println(this.getClass().getName());
			logger.trace("This is a trace message...");
			logger.debug("This is a debug message...");
			logger.info("This is a info message...");
			logger.warn("This is a warn message...");
			logger.error("This is a error message...");
			logger.fatal("This is a fatal message...");
//		}
	}

}
