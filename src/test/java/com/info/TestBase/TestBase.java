package com.info.TestBase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.Info.ExcelReader.DataRepository;
import com.Info.Utility.Driver;

/*import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;*/

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	public static Properties prop;
	public static String dest;
	public static String time;

	// public static ExtentHtmlReporter HtmlReporter;
	public static ExtentReports report;
	public static ExtentTest test;

	// static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");

	// int i=0;

	public TestBase() {
		try {
			File src = new File("E:\\StudyWorkpace\\com.info.avlara\\ConfigFile\\Config.property");
			FileInputStream fis = new FileInputStream(src);
			prop = new Properties();
			prop.load(fis);
		} catch (Exception ex) {
			System.out.println("Exception is" + ex.getMessage());
		}
	}

	@BeforeClass
	public void setUp() {
		try {
			Driver.Initialize();
			// String logpath = "log4j.properties";
			// PropertyConfigurator.configure(logpath);
		} catch (Exception ex) {
			System.out.println("Exception is" + ex.getMessage());
		}
	}

	@BeforeSuite
	public void Reportsetup() {
		try {

			report = new ExtentReports("E://GIT_Project//com.automation.maven//ReportGenration//Report" + System.currentTimeMillis() + ".html", true);
			report.addSystemInfo("HostName", "Pravin").addSystemInfo("Environment", "SIT").addSystemInfo("User", "Ambadas").addSystemInfo("Project Name", "Propchilli.com");
			report.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

		} catch (Exception ex) {
			System.out.println("Issue is" + ex.getMessage());
		}
	}

	public void getReport(ITestResult result) {
		try {
			String screnshotpath = takeScreenshot(Driver.Instance);
			if (result.getStatus() == ITestResult.FAILURE) {

				// String info=result.getThrowable();
				test.log(LogStatus.FAIL, result.getThrowable());
				test.log(LogStatus.FAIL, test.addScreenCapture(screnshotpath));
				test.log(LogStatus.FAIL, "Test Case Fail is:- " + result.getName());

				// test.addScreenCaptureFromPath("please refer below
				// screennshot",screnshotpath);

			} else if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(LogStatus.PASS, "Test Case pass is:- " + result.getName());
				test.log(LogStatus.PASS, "Below is the screen shot:-" + test.addScreenCapture(screnshotpath));
			} else if (result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, "test Case skip is:- " + result.getName());
			} else if (result.getStatus() == ITestResult.STARTED) {
				test.log(LogStatus.INFO, "Test Case started");

			}
			report.endTest(test);

		} catch (Exception es) {
			System.out.println(" Report genration Excepion is:- " + es.getMessage());
		}
	}

	@AfterMethod
	public void printReport(ITestResult result) {
		getReport(result);
	}

	public static String getApplicationURL() {
		String URL = prop.getProperty("URL");
		System.out.println("URL" + URL);
		return URL;
	}

	public static String getBrowserName() {
		String browser = prop.getProperty("browser");
		// System.out.println("browser name is" + browser);
		return browser;
	}

	public static String getChromePath() {
		String Chrome_path = prop.getProperty("chromeDriver");
		System.out.println("chrome path is" + Chrome_path);
		return Chrome_path;
	}

	public static String getExeclDataPath() {
		String datasheet_path = prop.getProperty("ExcelFile");
		System.out.println("Execl Path is" + datasheet_path);
		return datasheet_path;
	}

	public Iterator<String> getAllWindowhandle() {
		Set<String> window = Driver.Instance.getWindowHandles();
		Iterator<String> itr = window.iterator();
		return itr;
	}

	@AfterClass
	public void endTest() {

		report.flush();
		// report.close();
	}

	@AfterClass
	public void cleanUp() {
		try {
			Driver.close();
		} catch (Exception ex) {
			System.out.println("Exception is" + ex.getMessage());
		}
	}

	public void waitFor(int sec) throws InterruptedException {
		Thread.sleep(sec * 1000);
	}

	public static Object[][] getData(String ExcelName, String testcase) {
		// DataRepository Data = new
		// DataRepository("E:\\GIT_Project\\com.automation.maven\\TestDataFile\\Datasheet.xlsx");
		DataRepository Data = new DataRepository(getExeclDataPath());
		int rowNum = Data.getRowCount(testcase);
		System.out.println(rowNum);
		int colNum = Data.getColumnCount(testcase);

		Object sampleData[][] = new Object[rowNum - 1][colNum];
		for (int i = 2; i <= rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				sampleData[i - 2][j] = Data.getCellData(testcase, j, i);
				System.out.println("cell data is" + Data.getCellData(testcase, j, i));
			}
		}
		return sampleData;
	}

	public static String takeScreenshot(WebDriver driver) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); // 2016/11/16 12:08:43
			time = dateFormat.format(date);
			System.out.println("Time is" + time);
			TakesScreenshot tc = (TakesScreenshot) driver;
			File src = tc.getScreenshotAs(OutputType.FILE);

			dest = "E:\\GIT_Project\\com.automation.maven\\Screenshot\\" + time + ".png"; // for
			// extent
			// report

			File destination = new File(dest);
			FileUtils.copyFile(src, destination);
			System.out.println("image destination" + dest);
			System.out.println("Screen shot taken");

			// return dest;
		} catch (Exception ex) {
			System.out.println("Screenshot error is" + ex.getMessage());
		}
		return dest;
	}

	public static void updateResult(int indexSI, String testCaseName, String step_description, String testCaseStatus, String scriptName, boolean screenshot) throws IOException {

		try {
			String View = "View";
			String startDateTime = new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime());
			// String userDirector = System.getProperty("user.dir");
			String resultFile = "E:\\GIT_Project\\com.automation.maven\\ReportGenration\\TestHtmlReport.html";

			File file = new File(resultFile);
			System.out.println("file status is" + file.exists());
			// time = dateFormat.format(date);

			if (!file.exists()) {
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write("<html>" + "\n");
				bw.write("<head><title>" + "Automation Execution Report" + "</title>" + "\n");
				bw.write("<style>#customers {font-family: 'Trebuchet MS', Arial, Helvetica, sans-serif;border-collapse: collapse;width: 100%;font-size:12px;}" + "#customers td, #customers th {border: 1px solid #ddd;padding: 8px;}#customers tr:nth-child(even){background-color: #f2f2f2;}"
						+ "#customers tr:hover {background-color: #ddd;}#customers th {padding-top: 5px;padding-bottom: 5px;text-align: left;background-color: #4CAF50;color: white;} a {text-decoration: none;color:#51cc90}"
						+ "#table1 {margin-top: 50px;text-align: left; border: 2px solid #4CAF50;font-family: 'Trebuchet MS', Arial, Helvetica, sans-serif;border-collapse: collapse;width: 30%;font-size:12px;}"
						+ "#td1{ border: 1px solid #ddd; padding: 8px;}#th1{padding-top: 5px;padding-bottom: 5px;text-align: left;background-color: #4CAF50;color: white;width:100%}" + "\n");
				bw.write("</style>");
				bw.write("</head>" + "\n");
				bw.write("<body>");
				bw.write("<font <font face='Trebuchet MS'size='2' color='#52da6d'>" + "\n");
				bw.write("<u><h2 align='center'>" + "Automation Execution Report" + "</h2></u>" + "\n");
				bw.flush();
				bw.close();
			}
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(file, true));

			if (indexSI == 1) {
				bw1.write("<table id='customers'>");
				bw1.write("<tr>");
				bw1.write("<th width='70.6%'>Scenario Name :&nbsp;&nbsp;&nbsp;" + scriptName + " </th>");
				bw1.write("<th>Start Time :&nbsp;" + startDateTime + " </th>");
				bw1.write("</tr>");
				bw1.write("</table>");

				bw1.write("<table id='customers'>");
				bw1.write("<tr>");
				bw1.write("<th >S.No</th>");
				bw1.write("<th>Test case Name </th>");
				bw1.write("<th>Test Step Description </th>");
				bw1.write("<th>Result </th>");
				bw1.write("<th>Screenshot</th>");
				bw1.write("</tr>");

			}

			for (int i = 0; i <= indexSI; i++) {

				System.out.println("value of indexSI" + indexSI);
				bw1.write("<tr>");
				bw1.write("<td >" + indexSI + "</td>");
				bw1.write("<td>" + testCaseName + "</td>");
				bw1.write("<td>" + step_description + "</td>");
				if (testCaseStatus.equalsIgnoreCase("Pass")) {
					bw1.write("<td> <font color='#36b239'><b>" + testCaseStatus + "</b></font></td>");
				} else {
					bw1.write("<td ><font color='#fb5719'><b>" + testCaseStatus + "</b></font></td>");
				}
				if (screenshot == true) {
					// takeScreenshot(Driver.Instance);
					// bw1.write("<td>" + "<a
					// href='E:\\StudyWorkpace\\com.automation.maven\\Screenshot\\"
					// + dateFormat.format(date) + ".png'><b>" + View +
					// "</b></a>" + "</td>");
				} else {
					bw1.write("<td><font color='#fdb71c' >" + "Not Available" + "</font></td>");
				}
				bw1.write("</tr>");

				break;

			}

			// bw1.write("</table>");
			/*
			 * System.out.println("welcome");
			 * bw1.write("<tr>");bw1.write("<td>"); bw1.write(
			 * "out of table index value"+indexSI);
			 * bw1.write("</td>");bw1.write("</tr>");
			 */

			// bw1.write("</body>");
			// bw1.write("</html>");
			// bw1.write("out of table index value"+indexSI);
			bw1.flush();
			bw1.close();

			/*
			 * BufferedWriter bw2 = new BufferedWriter(new FileWriter(file,
			 * true)); System.out.println("out of table index value"+indexSI);
			 * bw2.write("<table id='table1'>"); bw2.write(
			 * "<tr><th id='th1'colspan='2' > Execution Summary</th></tr>");
			 * bw2.write(
			 * "<tr><td id='td1'> Executed By:</td><td id='td1'>Ambadas</td></tr>"
			 * ); bw2.write(
			 * "<tr><td id='td1'> Total Test cases Executed:</td><td id='td1'>3</td></tr>"
			 * ); bw2.write(
			 * "<tr><td id='td1'>Total Test cases Failed:</td><td id='td1'>1</td></tr>"
			 * ); bw2.write(
			 * "<tr><td id='td1'>Total Time Taken</td><td id='td1'>1 min 2 sec</td></tr>"
			 * ); bw2.write("</table>");
			 */

			// System.out.println("image destination1"+dest);

		} catch (Exception ex) {
			System.out.println("Html report problem is" + ex.getMessage());
		}
	}

}
