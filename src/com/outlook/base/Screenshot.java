package com.outlook.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

public class Screenshot 
{
	public static void takeScreenshot(TakesScreenshot driver, String folderPath)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
		String currentTime = format.format(new Date().getTime());
		try 
		{
			File source = driver.getScreenshotAs(OutputType.FILE);
			File desFolder = new File(folderPath);
			File destination = new File(desFolder.getAbsolutePath() + "/" + currentTime + ".jpg");
			if(!desFolder.exists())
				desFolder.mkdirs();
			FileUtils.copyFile(source, destination);
		} catch (WebDriverException e) 
		{
			logger.error("Failed to take screenshot!");
		} catch (IOException e) 
		{
			logger.error("Failed to save screenshot!");
		}
//		finally
//		{
//			logger.info("Screenshot is saved!");
//		}
	}
	
	private static Logger logger = LogManager.getLogger(Screenshot.class.getName());
}
