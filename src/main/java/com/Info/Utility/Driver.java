package com.Info.Utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.info.TestBase.TestBase;

public class Driver extends TestBase {

	public static WebDriver Instance = null;

	// static ReadProperty config=new ReadProperty();

	public static void Initialize() {
		try {
			if (Instance == null) {
				System.out.println("Initializing Driver");
				System.out.println("Driver name is" + getBrowserName());
				if (getBrowserName().equalsIgnoreCase("firefox")) {
					Instance = new FirefoxDriver();
					// System.out.println(" Driver name
					// is"+getBrowserName().equalsIgnoreCase("firefox"));
				} else if (getBrowserName().equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver", getChromePath());
					Instance = new ChromeDriver();
				} else if (getBrowserName().equalsIgnoreCase("IE")) {
					Instance = new InternetExplorerDriver();
				}
				Instance.manage().timeouts().pageLoadTimeout(200, TimeUnit.SECONDS);
				Instance.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				Instance.manage().window().maximize();
			}
		} catch (Exception ex) {
			System.out.println("xception in driver class is" + ex.getMessage());
		}

	}

	public static void close() {
		System.out.println("Closing Browser");
		// Instance.close();
		// Instance=null;
	}

	public void quit() {
		System.out.println("Closing Browser");
		Instance.quit();
		Instance = null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Data");
		Driver d1 = new Driver();
		d1.Initialize();
	}
}
