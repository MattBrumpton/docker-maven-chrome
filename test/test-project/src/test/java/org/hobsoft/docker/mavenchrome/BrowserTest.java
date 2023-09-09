package org.hobsoft.docker.mavenchrome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrowserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserTest.class);
    private static final String GOOGLE_URL = "https://www.google.com";
    private static final String SEARCH_BOX_NAME = "q";
    private static final String SEARCH_QUERY = "OpenAI";

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        LOGGER.info("Setting up the browser.");
        try {
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-debugging-port=9222");
            driver = new ChromeDriver(options);
        } catch (Exception e) {
            LOGGER.error("Exception during browser setup: ", e);
        }
    }

    @AfterEach
    public void tearDown() {
        LOGGER.info("Tearing down the browser.");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void checkBrowser() {
        try {
            navigateToGoogle();
            performSearch();
            waitForResultsAndVerify();
        } catch (Exception e) {
            LOGGER.error("Exception during test: ", e);
        }
    }

    private void navigateToGoogle() {
        driver.get(GOOGLE_URL);
    }

    private void performSearch() {
        driver.findElement(By.name(SEARCH_BOX_NAME)).sendKeys(SEARCH_QUERY);
        driver.findElement(By.name(SEARCH_BOX_NAME)).sendKeys(Keys.RETURN);
    }

	private void waitForResultsAndVerify() {
		WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), SEARCH_QUERY));
		assertTrue(driver.getPageSource().contains(SEARCH_QUERY));
	}
	
}
