package Grid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;


public class FirefoxNode {
	
	WebDriver driver;
	String nodeUrl;
	String pswrdErrorMessage;
	String nonexistentEmailMessage;
  @BeforeTest
  public void OpenBrowser() throws InterruptedException {
	  try {
			nodeUrl = "http://192.168.1.10:4444/wd/hub";
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setBrowserName("firefox");
			capabilities.setPlatform(Platform.WINDOWS);
			driver = new RemoteWebDriver(new URL(nodeUrl), capabilities);
			driver.manage();
			driver.get("http://www.yahoo.com");
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(2000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
  }
  @Test
  public void SigninButton() throws InterruptedException {
		try {
			driver.findElement(By.xpath("//a[@class='_yb_1ana4']")).click();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
  }
  @Test(dependsOnMethods= {"SigninButton"})
  public void InputValidEmail() throws InterruptedException {
	  try {
		  driver.findElement(By.xpath("//input[@id='login-username']")).sendKeys("chrisryanbaltazar@yahoo.com");
		  Thread.sleep(2000);
		  driver.findElement(By.xpath("//input[@id='login-signin']")).click();
		  Thread.sleep(2000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
  }
  @Test(dependsOnMethods= ("InputValidEmail"))
  public void InputValidPassword() throws InterruptedException {
	  driver.findElement(By.xpath("//input[@id='login-passwd']")).sendKeys("*********");
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("//button[@id='login-signin']")).click();
	  Thread.sleep(2000);
  }
  @Test(dependsOnMethods= ("InputValidPassword"))
  public void TestSignout() throws InterruptedException {
	  try {
		driver.findElement(By.xpath("//span[@class='_yb_oza1t _yb_1c796 _yb_t4gjo']")).click();
		  Thread.sleep(2000);
		  driver.findElement(By.xpath("//span[@class='_yb_1cqr5 _yb_t4gjo _yb_2loff']")).click();
		  Thread.sleep(2000);
		  SigninButton();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
  }
  @Test(dependsOnMethods= ("TestSignout"))
  public void TestSavedAccount() throws InterruptedException {
	  try {
		driver.findElement(By.xpath("//a[@name='username']")).click();
		  Thread.sleep(2000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
  }
  @Test(dependsOnMethods= ("TestSavedAccount"))
  public void InputWrongPassword() throws InterruptedException {
	  try {
		driver.findElement(By.xpath("//input[@id='login-passwd']")).sendKeys("*****");
		  Thread.sleep(2000);
		  driver.findElement(By.xpath("//button[@id='login-signin']")).click();
		  Thread.sleep(2000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	} 
  }
  @Test(dependsOnMethods = ("InputWrongPassword"))
  public void pswrdTestErrorMessage() throws InterruptedException{
	  try {
		String EM = "Invalid password. Please try again";
		  pswrdErrorMessage = driver.findElement(By.xpath("//p[@class='error-msg']")).getText();
		  if(pswrdErrorMessage.equals(EM)) {
			  System.out.println("Firefox" + "\n" + "Password Expected and Actual Error Message Matched:" + "\n" + pswrdErrorMessage);
		  }else {
			  System.out.println("Firefox" + "\n" + "Password Expected and Actual Error Message Mismatched:" + pswrdErrorMessage);
		  }
		  Thread.sleep(2000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	} 
  }
  @Test(dependsOnMethods = ("pswrdTestErrorMessage"))
  public void testUseAnotherAccount() throws InterruptedException {
	  driver.navigate().to("https://login.yahoo.com");
	  Thread.sleep(2000);
	  driver.findElement(By.linkText("Use another account")).click();
	  Thread.sleep(2000);
  }
  @Test(dependsOnMethods = ("testUseAnotherAccount"))
  public void testNonExistentEmail() throws InterruptedException {
	  driver.findElement(By.xpath("//input[@id='login-username']")).sendKeys("fortesting");
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("//input[@id='login-signin']")).click();
	  Thread.sleep(2000);
  }
  @Test(dependsOnMethods = ("testNonExistentEmail"))
  public void nonexistentErrorMessage() throws InterruptedException{
	  try {
		String EM = "Sorry, we don't recognize this email.";
		nonexistentEmailMessage = driver.findElement(By.xpath("//p[@id='username-error']")).getText();
		  if(nonexistentEmailMessage.equals(EM)) {
			  System.out.println("Firefox" + "\n" + "Email Expected and Actual Error Message Matched:" + "\n" + nonexistentEmailMessage);
		  }else {
			  System.out.println("Firefox" + "\n" + "Email Expected and Actual Error Message Mismatched:" + nonexistentEmailMessage);
		  }
		  Thread.sleep(2000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	} 
  }
  @Test(dependsOnMethods = ("nonexistentErrorMessage"))
  public void testonlyUsername() throws InterruptedException{
	  driver.findElement(By.xpath("//input[@id='login-username']")).clear();
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("//input[@id='login-username']")).sendKeys("chrisryanbaltazar");
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("//input[@id='login-signin']")).click();
	  Thread.sleep(2000);
  }
  @AfterTest
  public void CloseBrowser() {
	  driver.quit();
  }

}
