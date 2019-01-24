package com.info.TestRunner;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.Info.Utility.Driver;
import com.info.TestBase.TestBase;
import com.info.pageObjects.FirstCry;
import com.relevantcodes.extentreports.LogStatus;

public class TestRunner extends TestBase {

	int indexSI = 1;

	FirstCry cry;

	@Test(priority = 1)
	public void LaunchApplichation() throws IOException {
		Driver.Instance.get(getApplicationURL());
		cry = PageFactory.initElements(Driver.Instance, FirstCry.class);

		test = report.startTest("Launch application");
		String title = Driver.Instance.getTitle();

		if (title.contains("firstCry")) {

			test.log(LogStatus.INFO, "Application is up and running");
			test.log(LogStatus.PASS, " Page Title verify");
			// Assert.assertTrue(true, "Application is up and running");

		} else {
			test.log(LogStatus.INFO, "Issue with application launch");
			test.log(LogStatus.FAIL, " Page Title not verify");
			// Assert.assertTrue(false);

		}
	}

	@Test(priority = 2)
	public void tovalidateFirstCry() {
		try {
			test = report.startTest("Validating Post requirement form");
			test.log(LogStatus.INFO, "Validating Post requirement form");

			cry.To_Fill_Customer_Enquiry_Details("footware");

			boolean msg1 = cry.To_verify_postReq_details();
			if (msg1 == true) {
				Assert.assertTrue(true, "User has successfully post requirment");
				test.log(LogStatus.PASS, "User has successfully post requirment");

			} else {
				Assert.assertTrue(false, "Requirment is not posted due to some issue");
				test.log(LogStatus.FAIL, "Requirment is not posted due to some issue");

			}
		} catch (Exception ex) {
			System.out.println("Exception is post req is" + ex.getMessage());
		}

	}

}
