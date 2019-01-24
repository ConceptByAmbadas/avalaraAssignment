package com.info.pageObjects;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.info.TestBase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class FirstCry extends TestBase {

	final WebDriver driver;

	public FirstCry(WebDriver driver) {

		this.driver = driver;
	}

	Random rand = new Random();
	By btnClose = By.xpath(".//*[@class='_pop_close _pop_reg_bg']");
	By searchbox = By.xpath("//*[@id='search_box']");
	By customer_Email = By.xpath("//*[contains(text(),'Contact Us')]");
	By requriment_description = By.xpath("//*[@name='feedbackDesc']");
	By Customer_mobile = By.xpath("//*[@name='mb']");
	By btn_Submit = By.xpath("//*[@id='btn' and @value='SUBMIT']");

	public void To_Fill_Customer_Enquiry_Details(String Name) {

		try {

			driver.findElement(btnClose).click();
			driver.findElement(searchbox).sendKeys(Name);

			driver.findElement(btn_Submit).click();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean To_verify_postReq_details() {
		test = report.startTest("To_verify_Customer_Enquiry_form");
		test.log(LogStatus.INFO, "Verifying Enquiry form");
		String msg = driver.findElement(By.xpath(".//*[@id='alternatecolor']/tbody/tr[1]/td/font")).getText();

		if (msg.contains("Thank You!")) {
			System.out.println("IN IF Message verified is" + msg);
			return true;
		} else {
			System.out.println("IN ELSE Message verified is" + msg);
			return false;
		}

	}
}
