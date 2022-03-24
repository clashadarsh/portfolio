package com.example;

import java.io.IOException;

import com.pageObjects.LandingPage;
import com.resources.Base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ValidateNavBar extends Base {
    public WebDriver driver;
    public static Logger log = LogManager.getLogger(Base.class.getName());
    @BeforeTest
    public void init() throws IOException {
        driver = initializeDriver();
        log.info("driver eddide");
        driver.get(prop.getProperty("url"));
        log.info("url hit");
    }
    @Test
    public void validateNavBAr() throws IOException {
        LandingPage l = new LandingPage(driver); 
        Assert.assertTrue(l.getNavBar().isDisplayed());
        log.info("navbar kanistha ide");
        Assert.assertFalse(false);//yavaglu false irbeku anta assertion
    }
    @AfterTest
    public void teardown() {
        driver.close();
    }
}
