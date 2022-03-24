package com.example;

import java.io.IOException;

import com.pageObjects.ForgotPassword;
import com.pageObjects.LandingPage;
import com.pageObjects.LoginPage;
import com.resources.Base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePage extends Base {
    public WebDriver driver;
    public static Logger log = LogManager.getLogger(Base.class.getName());
    @BeforeTest
    public void init() throws IOException {
        driver = initializeDriver();
        log.info("driver is initializzed");
    }
    @Test(dataProvider = "getData")
    public void basePageNavigation(String user, String pwd, String type) throws IOException {
        driver.get(prop.getProperty("url"));
        LandingPage l = new LandingPage(driver);
        LoginPage lp = l.getLogin();
        lp.getEmail().sendKeys(user);
        lp.getPassword().sendKeys(pwd);
        lp.getLogin().click();
        log.info(type);
        lp.getLogin().click();
        ForgotPassword fp = lp.forgotPassword();
        fp.getEmail().sendKeys("xxx@gmail");
        fp.sendMeInstructions().click();
    }
    @AfterTest
    public void teardown() {
        driver.close();
    }

    @DataProvider
    public Object[][] getData() {
        Object[][] data = new Object[2][3];//Two users three infos
        data[0][0] = "anna@gmail.com";
        data[0][1] = "12345";
        data[0][2] = "goldUser";

        data[1][0] = "thamma@gmail.com";
        data[1][1] = "12345";
        data[1][2] = "BronzeUser";
        
        return data;
    }
}
