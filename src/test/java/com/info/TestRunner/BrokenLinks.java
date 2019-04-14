package com.info.TestRunner;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLinks {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "E:\\Seleium_data\\All_FireFox_version\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("www.google.co.in");

		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("link are size" + links.size());
		for (int i = 0; i < links.size(); i++) {
			WebElement ele = links.get(i);
			String url = ele.getAttribute("href");
			verifylinks(url);

		}

	}

	public static void verifylinks(String activeurl) {
		try {
			URL url = new URL(activeurl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(3000);
			con.connect();
			if (con.getResponseCode() == 200) {
				System.out.println("active link with 200 code" + con.getResponseMessage());

			}

			if (con.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println("broken lik" + con.getResponseMessage());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
