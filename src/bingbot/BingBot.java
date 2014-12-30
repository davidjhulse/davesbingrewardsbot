/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bingbot;

import java.io.File;
import java.util.Random;
import java.util.Scanner;
import com.thoughtworks.selenium.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.server.*;

/**
 *
 * @author Quincy
 */
public class BingBot extends SeleneseTestCase {
    
    private SeleniumServer seleniumServer;
    static String[] list = new String[41238];
    private Scanner scanner;
    private RemoteControlConfiguration rc;
    private FirefoxProfile profile;
    private WebDriver driver;
    public BingBot(boolean mobile) {
        rc = new RemoteControlConfiguration();
        rc.setSingleWindow(false);

        profile = new FirefoxProfile();
        if (mobile) {
            profile.setPreference("general.useragent.override", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }
        driver = new FirefoxDriver(profile);
        try {
            seleniumServer = new SeleniumServer();
            seleniumServer.start();
        } catch (Exception ex) {
            Logger.getLogger(BingBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param n number of searches to execute
     * @param file file to get user data from
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public void search(int m, int n, File file) throws Exception {
        System.out.println("Search method entered...");
        
        scanner = new Scanner(file);
        Scanner sc = new Scanner(new File("./files/dict"));
        
        String next = scanner.next();
        String name = null;
        String password = null;
        while(!next.equals("END")){
           name = next;
           password = scanner.next();
           next = scanner.next();
        }
        
        
        for (int k = 0; k < m; k++) {

            driver.get("http://live.com");
            try {
                WebElement signout = driver.findElement(By.id("c_signout"));
                    if(!signout.isDisplayed()) {
                        return;
                    }
            
            } catch (Exception e) {
                System.out.println("NO SUCH ELEMENT 'C_SIGNOUT'");
                System.out.println("SIGNING IN");
                
            }
            
            WebElement username = driver.findElement(By.id("i0116"));
            username.sendKeys(name);
            WebElement userpassword = driver.findElement(By.id("i0118"));
            userpassword.sendKeys(password);
            WebElement signin = driver.findElement(By.id("idSIButton9"));
            signin.click();
            Thread.sleep(10000);
            for (int j = 0; j < 41238; j++) {
                String word = sc.next();
                list[j] = word;
            }
            for(int i = 0; i < n; i++) {
                Random rand = new Random();
                driver.get("https://bing.com/");
                WebElement query = driver.findElement(By.id("sb_form_q"));
                query.sendKeys(list[rand.nextInt(41238)]);
                WebElement go = driver.findElement(By.id("sb_form_go"));
                go.click();
                Thread.sleep(rand.nextInt(5000)+5000);
            }
        }
        seleniumServer.stop();
    }
    
    public void searchMobile(int m, int n, File file) throws Exception {
        System.out.println("Search method entered...");
        
        scanner = new Scanner(file);
        Scanner sc = new Scanner(new File("./files/dict"));
        
        String next = scanner.next();
        String name = null;
        String password = null;
        while(!next.equals("END")){
           name = next;
           password = scanner.next();
           next = scanner.next();
        }
        
        
        for (int k = 0; k < m; k++) {

            driver.get("http://live.com");
            try {
                WebElement signout = driver.findElement(By.linkText("Sign out"));
                signout.click();
            } catch (Exception e) {
                System.out.println("NO SUCH ELEMENT 'C_SIGNOUT'");
                System.out.println("SIGNING IN");
                
            }
            
            WebElement username = driver.findElement(By.id("i0116"));
            username.sendKeys(name);
            WebElement userpassword = driver.findElement(By.id("i0118"));
            userpassword.sendKeys(password);
            WebElement signin = driver.findElement(By.id("idSIButton9"));
            signin.click();
            for (int j = 0; j < 41238; j++) {
                String word = sc.next();
                list[j] = word;
            }
            for(int i = 0; i < n; i++) {
                Random rand = new Random();
                driver.get("https://bing.com/");
                WebElement query = driver.findElement(By.id("sb_form_q"));
                query.sendKeys(list[rand.nextInt(41238)]);
                WebElement go = driver.findElement(By.id("sbBtn"));
                go.click();
                Thread.sleep(rand.nextInt(5000)+5000);
            }
        }
        seleniumServer.stop();
    }
    
    
}
