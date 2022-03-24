package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage {
    public WebDriver driver;
    By signin = By.cssSelector("a[href*='sign_in'");
    By title = By.cssSelector(".text-center>h2");
    By navBar = By.cssSelector(".nav.navbar-nav.navbar-right>li>a");

    public LandingPage(WebDriver drivercoming) {
        this.driver = drivercoming;
    }
    public LoginPage getLogin() {
        driver.findElement(signin).click();
        LoginPage l = new LoginPage(driver);
        return l;
    }
    public WebElement getTitle() {
        return driver.findElement(title);
    }
    public WebElement getNavBar() {
        return driver.findElement(navBar);
    }
}
