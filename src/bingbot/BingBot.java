/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bingbot;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import com.thoughtworks.selenium.*;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import java.util.ArrayList;


/**
 *
 * @author Quincy
 */
public class BingBot extends SeleneseTestCase {
    
    
    static String[] list = new String[41238];
    /**
     * @param n number of searches to execute
     * @param file file to get user data from
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public void search(int m, int n, File file) throws Exception {
        
        setUp();
        Scanner scanner = new Scanner(file);
        for (int k = 0; k < m; k++) {
            testNew(scanner);
            Scanner sc = new Scanner(new File("./files/dict"));
            for (int j = 0; j < 41238; j++) {
                String word = sc.next();
                list[j] = word;
            }
            for(int i = 0; i < n; i++) {
                Random rand = new Random();
                selenium.open("https://bing.com/search?q=" + list[rand.nextInt(41238)]);
                Thread.sleep(5000);
            }
        } 
    }
    
    public void setUp() throws Exception {
        setUp("http://www.live.com/", "*firefox C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
    }
      public void testNew(Scanner sc) throws Exception {
          selenium.open("/");
          if (selenium.isElementPresent("c_signout")) {
              selenium.open("https://login.live.com/logout.srf?ct=1419813731&amp;rver=6.4.6456.0&amp;lc=1033&amp;id=64855&amp;ru=https:%2F%2Fbay180.mail.live.com%2Fhandlers%2FSignout.mvc%3Fservice%3DLive.Mail%26mkt%3Den-us&amp;mkt=en-us");
              Thread.sleep(20000);
              selenium.open("/");
              Thread.sleep(5000);
          }
          String next = sc.next();
          String name = null;
          String password = null;
          while(!next.equals("END")){
              name = next;
              password = sc.next();
              next = sc.next();
          }
          selenium.type("i0116", name);
          selenium.type("i0118", password);
          selenium.click("SI");
          
          Thread.sleep(20000);
    }
    
}
