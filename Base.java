package com.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
    public WebDriver driver;
    public Properties prop;
    public WebDriver initializeDriver() throws IOException{
        prop = new Properties();
        FileInputStream fil = new FileInputStream("C:\\Users\\aniranjanamu\\Desktop\\Tee\\demo\\src\\main\\java\\com\\resources\\data.properties");
        prop.load(fil);
        String browserName = prop.getProperty("browser");
        
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equals("IE")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;     
    }

    public String getScreenShotPath(String testCaseName, WebDriver drivercoming) throws IOException {
        driver = drivercoming;
        TakesScreenshot ts = (TakesScreenshot)drivercoming;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;//we use it to pull and attach screenshot in extent html report
    }
}
