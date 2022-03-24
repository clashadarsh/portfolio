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

public class ValidateTitle extends Base{
    public WebDriver driver;
    public static Logger log = LogManager.getLogger(Base.class.getName());
    @BeforeTest
    public void init() throws IOException {
        driver = initializeDriver();
        log.info("driver initialized");
        driver.get(prop.getProperty("url"));
        log.info("url hit");
    }
    @Test
    public void validateTitle() throws IOException {
        LandingPage l = new LandingPage(driver);
        Assert.assertEquals(l.getTitle().getText(), "FEATURED COURSES12");
        log.info("hu title text validated");     
    }
    @AfterTest
    public void teardown() {
        driver.close();
    }
}