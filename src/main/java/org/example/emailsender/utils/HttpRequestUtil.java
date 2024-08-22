package org.example.emailsender.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

public class HttpRequestUtil {

    // Initialize WebDriver for Selenium
    private WebDriver initWebDriver() {
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.chrome.driver", "C:/Users/user/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");

        // Setup Chrome options to run headless
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run headless mode
        options.addArguments("--disable-gpu"); // Applicable to Windows to avoid potential bugs
        options.addArguments("--no-sandbox"); // Bypass OS security model

        // Initialize ChromeDriver
        return new ChromeDriver(options);
    }

    public int getInstagramFollowersFromHtml(String instagramUrl) {
        WebDriver driver = initWebDriver();

        try {
            // Navigate to the Instagram profile page
            driver.get(instagramUrl);

            // Wait for the followers count element to be present
            WebElement followersElement = driver.findElement(By.xpath("//a[contains(@href, '/followers/')]/span"));

            // Get the followers count from the "title" attribute
            String followersCount = followersElement.getAttribute("title");

            // Convert the followers count to an integer
            return parseFollowerCount(followersCount);

        } finally {
            // Close the browser
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private int parseFollowerCount(String followerCountString) {
        // Handle abbreviated numbers like "1.5k", "2m", etc.
        followerCountString = followerCountString.replace("&nbsp;", "").replace("хил.", "000");
        if (followerCountString.contains("k")) {
            followerCountString = followerCountString.replace("k", "000");
        } else if (followerCountString.contains("m")) {
            followerCountString = followerCountString.replace("m", "000000");
        } else if (followerCountString.contains("b")) {
            followerCountString = followerCountString.replace("b", "000000000");
        }
        return Integer.parseInt(followerCountString.replaceAll("[^\\d]", ""));
    }
}
