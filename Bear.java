package com.example;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Bear {
  public static void main(String[] args) throws IOException, InterruptedException, ParseException {

    String disputeManager = "Dispute Manager [www.client-central.com]";
    // String cardManagement = "CWSi | Card Management - Choose Client ";
    String disputeDetail = "Repository Viewer - Dispute Detail [www.client-central.com]";
    String disputeSearch = "Repository Viewer - Dispute Search [www.client-central.com]";
    String disputeActionDetail = "Repository Viewer - Dispute Action Detail [www.client-central.com]";
    String disputeActivityDetail = "Repository Viewer - Activity Detail [www.client-central.com]";
    String spc = "CWSi | SPC - Retrieve and Select Transaction";

    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Token:");
    String token = scanner.nextLine();
    scanner.close();
    // System.setProperty("webdriver.chrome.driver", "D:\\Users\\chromedriver.exe");
    // ChromeOptions options = new ChromeOptions();
    // options.addArguments("--headless");     
    // options.addArguments("--disable-gpu");
    // options.addArguments("--window-size=1920,1080");
    // options.addArguments("--start-maximized");
    // WebDriver driver = new ChromeDriver(options);
    // System.setProperty("webdriver.chrome.driver", "D:\\Users\\chromedriver.exe");
    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.client-central.com");
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.findElement(By.id("Ecom_User_ID")).sendKeys("amurth00");
    driver.manage().window().maximize();
    driver.findElement(By.id("Ecom_Token")).sendKeys(token);
    driver.findElement(By.id("loginButton2")).click();
    // switching to iframe
    String homeWindowHandleID = driver.getWindowHandle();
    // String homeWindowTitle = driver.getTitle(); Switchingoo nu use madbodu
    System.out.println("Parent window: " + homeWindowHandleID);
    driver.switchTo().frame("main");
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    System.out.println("We are switching to the iframe");
    driver.findElement(By.xpath("//a[contains(text(),'Dispute Manager')]")).click();
    // switching to Dispute Manager

    Pad pad = new Pad();

    pad.switchingoo(driver, disputeManager);
    // if (driver.getTitle().contains("Dispute Manager [www.client-central.com]")) {

    // }
    driver.findElement(By.xpath("//*[@id='fmForm:j_id103:1:j_id108']")).click();/** Dispute */
    Thread.sleep(900);
    driver.findElement(By.id("fmForm:j_id124:0:j_id137")).click();/** Rep Viewer */
    // fmForm:j_id124:0:j_id137
    LinkedHashSet<String> hash_Set =  pad.readExcel();

    int rowPicker = 1;
    for (String disputeID : hash_Set) {
      driver.findElement(By.xpath("//*[@id='fmForm:evalId']")).clear();
      driver.findElement(By.xpath("//*[@id='fmForm:evalId']")).sendKeys(disputeID);
      Select drpType = new Select(driver.findElement(By.name("fmForm:actnTypeByNameFndrElem")));/** Handling dropdown */
      drpType.selectByVisibleText("Accel Dispute Inquiry");
      driver.findElement(By.xpath("//*[@id='fmForm:btnScan']")).click();
      driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      // bringing in javascript since element is hidden
      WebElement searchedDispute = driver.findElement(By.xpath("//*[@id='fmForm:rptEval:0:j_id854']"));
      String id = searchedDispute.getText();
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].click()", searchedDispute);
        
      // switching to Repository Viewer;
      pad.switchingoo(driver, disputeDetail);

      Select select = new Select(driver.findElement(By.id("fmForm:evalResnByNamePckrElem")));
      WebElement option = select.getFirstSelectedOption();
      String defaultItem = option.getText();

      if (defaultItem == "") {
        // String ID = driver.findElement(By.id("//*[@id='fmForm:j_id135']/td[3]")).getText();
        String cardNumber = driver.findElement(By.xpath("//*[@id='fmForm:j_id249']/td[3]/span")).getText();
        String amount = driver.findElement(By.xpath("//tbody/tr[@id='fmForm:j_id253']/td[3]")).getText();
        String date = driver.findElement(By.xpath("//tbody/tr[@id='fmForm:j_id273']/td[3]")).getText();
        WebElement searcheddispute2 = driver.findElement(By.id("fmForm:tabDtl:rptActn:0:j_id300"));
        JavascriptExecutor js2 = (JavascriptExecutor)driver;
        js2.executeScript("arguments[0].click()", searcheddispute2);
        driver.close();
        
        pad.switchingoo(driver, disputeActionDetail);
        String addInfo = driver.findElement(By.xpath("//tbody/tr[@id='fmForm:tabDtl:j_id291']/td[3]")).getText();
        driver.findElement(By.id("fmForm:btnActn")).click();
        driver.findElement(By.id("fmForm:btnmActn:j_id111")).click();
        driver.close();
        
        pad.switchingoo(driver, disputeActivityDetail);
        String logo = driver.findElement(By.xpath("//*[@id='fmForm:fitmRpsyVewrActyDtlDestInfoBid']/td[3]")).getText();
        String cardAcceptorName = driver.findElement(By.xpath("//tbody/tr[@id='fmForm:fitmRpsyVewrActyDtlOrigCardAcprInfoAcqOwn']/td[3]")).getText();
        String stan = driver.findElement(By.xpath("//tbody/tr[@id='fmForm:fitmRpsyVewrActyDtlTranInfoStan']/td[3]")).getText();
        
        driver.close();
        
        // String expiry = pad.getExpiry(driver, homeWindowHandleID, cardManagement, logo, cardNumber);

        // fetch Claim Number
        String claimNumber = pad.claimFetcher(driver, homeWindowHandleID, spc, logo, cardNumber, stan);
        
        // fetch pos input  and entry mode
        String formattedDate = pad.dateFormatter(date);
        // System.out.println(formattedDate);
        
        String[] pos=  pad.posFetcher(driver, homeWindowHandleID, spc, logo, cardNumber, formattedDate, stan);
        String entryMode = pos[0];
        String posInput = pos[1];
        
        pad.switchingoo(driver, disputeSearch);
        pad.writeExcel(rowPicker, addInfo, cardAcceptorName, amount, date, stan, logo, cardNumber, claimNumber, entryMode, posInput, id);
        // pad.writeExcel(rowPicker, addInfo, cardAcceptorName, amount, date, stan, logo, cardNumber, claimNumber);
        
        System.out.println(rowPicker+" :Fetched Data for: " +disputeID);
        rowPicker++;

      }else{

        driver.close();
        pad.switchingoo(driver, disputeSearch);
        pad.touchExcel(rowPicker, defaultItem);
        System.out.println(rowPicker+" :Fetched Data for: " +disputeID);
        rowPicker++;

      }
 
    }/**FOR LOOP END*/
    
    JavascriptExecutor js = (JavascriptExecutor)driver;
    js.executeScript("alert('*****Fetched ra mama*****')");
  
  }

    
}