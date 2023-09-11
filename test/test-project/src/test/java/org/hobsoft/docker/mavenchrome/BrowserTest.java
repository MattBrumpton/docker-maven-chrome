package org.hobsoft.docker.mavenchrome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Other imports
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.util.logging.Level;
import org.openqa.selenium.remote.CapabilityType;

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
            // System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
            System.setProperty("webdriver.chrome.verboseLogging", "true");
            System.setProperty("webdriver.chrome.driver.host", "127.0.0.1");

            ChromeOptions options = new ChromeOptions();
            LoggingPreferences logs = new LoggingPreferences(); // Added for logging
            logs.enable(LogType.DRIVER, Level.ALL); // Added for logging

            options.setCapability("goog:loggingPrefs", logs); // Added for logging
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            // options.addArguments("--disable-setuid-sandbox");
            // options.addArguments("--remote-debugging-port=9222");
            // options.addArguments("--disable-dev-shm-usage");
            // options.addArguments("--allowed-ips=127.0.0.1"); // Moved to last for better
            // visibility

            driver = new ChromeDriver(options);
        } catch (Exception e) {
            LOGGER.error("Failed to set up browser: " + e.getMessage());
            throw new RuntimeException("Failed to set up browser", e);
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
            assertTrue(driver.getPageSource().contains(SEARCH_QUERY));
        } catch (Exception e) {
            LOGGER.error("Exception in checkBrowser: ", e);
            fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    private void navigateToGoogle() {
        driver.get(GOOGLE_URL);
    }

    private void performSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name(SEARCH_BOX_NAME)));
        searchBox.sendKeys(SEARCH_QUERY);
        searchBox.sendKeys(Keys.RETURN);
    }

    private void waitForResultsAndVerify() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search"))); // This ID is typically associated
                                                                                    // with the search results container
                                                                                    // on Google.
        assertTrue(driver.getPageSource().contains(SEARCH_QUERY));
    }

}
