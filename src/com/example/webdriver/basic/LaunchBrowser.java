package com.example.webdriver.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class LaunchBrowser {
	
	private static final String CHROME_DRIVER = "webdriver.chrome.driver";
    
	private static final Map<String, WebDriver> WEB_DRIVERS = new HashMap<>();

	public static void main(String[] args) throws InterruptedException {
		setupWebDrivers();

		final WebDriver driver = WEB_DRIVERS.get(CHROME_DRIVER);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		driver.navigate().to("https://amazon.in");
		driver.manage().window().maximize();
		
		navigateToBooksPage(driver);
		searchBook(driver, "John Grisham");
		
		clickOnFoundResult(driver, "The Firm");
		
		manageTabs(driver);
		addingToTheCart(driver);
		
		driver.close();
		driver.quit();
	}
	
	private static void manageTabs(final WebDriver driver) throws InterruptedException {
		final Set<String> handlers = driver.getWindowHandles();
		final String currentTab = driver.getWindowHandle();
		handlers.remove(currentTab);
		
		final String newCurrentTab = handlers.iterator().next();
		
		if (!newCurrentTab.equals(currentTab)) {
			driver.switchTo().window(newCurrentTab);
		}
		Thread.sleep(2000);
	}
	
	private static void addingToTheCart(final WebDriver driver) {
		driver.findElement(By.id("add-to-cart-button")).click();
	}
	
	private static void clickOnFoundResult(final WebDriver driver, final String titleName) throws InterruptedException {
		driver.findElement(By.linkText(titleName)).click();
		Thread.sleep(2000);
	}
	
	private static void searchBook(final WebDriver driver, final String term) throws InterruptedException {
		final WebElement search = driver.findElement(By.id("twotabsearchtextbox"));
		search.sendKeys(term);
		
		driver.findElement(By.xpath("//*[@id=\"nav-search\"]/form/div[2]/div/input")).click();
	}

	private static void navigateToBooksPage(final WebDriver driver) throws InterruptedException {
		final WebElement category = driver.findElement(By.cssSelector("#nav-link-shopall"));
		Thread.sleep(2000);
		
		final Actions action = new Actions(driver);
		action.moveToElement(category).perform();
		Thread.sleep(2000);
		
		final WebElement books = driver.findElement(By.xpath("//*[@id=\"nav-flyout-shopAll\"]/div[2]/span[15]"));
		action.moveToElement(books).perform();
		Thread.sleep(2000);
		
		final WebElement fictionBooks = driver.findElement(By.linkText("Fiction Books"));
		fictionBooks.click();
		Thread.sleep(2000);
	}
	
	private static void setupWebDrivers() {
		System.setProperty(CHROME_DRIVER, ".\\driver\\chromedriver.exe");
		WEB_DRIVERS.put(CHROME_DRIVER, new ChromeDriver());
	}
}
