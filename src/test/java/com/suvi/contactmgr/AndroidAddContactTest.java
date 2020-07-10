package com.suvi.contactmgr;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class AndroidAddContactTest {
    private static final Logger logger = LoggerFactory.getLogger(AndroidAddContactTest.class);

    WebDriver driver;

    @Before
    public void setup() throws MalformedURLException, URISyntaxException {
        URL appiumUrl = new URL("http://localhost:4723/wd/hub");
        URL resource = getClass().getClassLoader().getResource("apps/ContactManager.apk");
        assert resource != null;
        File app = Paths.get(resource.toURI()).toFile();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("deviceName", "Nexus 5X API 29 x86");
        capabilities.setCapability("app", app.getAbsolutePath());
        driver = new AndroidDriver<MobileElement>(appiumUrl, capabilities);
        logger.info("Setup Complete");
        AndroidDriver<AndroidElement> d = new AndroidDriver<>(appiumUrl, capabilities);


    }

    @After
    public void tearDown() {
        driver.quit();
        logger.info("Teardown complete");
    }

    @Test
    public void testAddContact() {
        logger.info("Starting testAddContact");
        WebElement addContact = driver.findElement(By.id("com.example.android.contactmanager:id/addContactButton"));
        addContact.click();
        WebDriverWait waitForContactScreen = new WebDriverWait(driver, 10);
        WebElement contactName = waitForContactScreen.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title"))
        );
        WebElement contactNameTextField = driver.findElement(By.id("com.example.android.contactmanager:id/contactNameEditText"));
        contactNameTextField.sendKeys("My Test Contact");
        WebElement phoneNo = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.ScrollView/android.widget.TableLayout/android.widget.TableRow[6]/android.widget.EditText"));
        phoneNo.sendKeys("5678901234");
        WebElement email = driver.findElement(By.id("com.example.android.contactmanager:id/contactEmailEditText"));
        WebElement save = driver.findElement(By.className("android.widget.Button"));
        save.click();
        WebDriverWait waitForHomeScreen = new WebDriverWait(driver, 10);
        WebElement list = waitForHomeScreen.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.android.contactmanager:id/contactList")));
        List<WebElement> widgets = list.findElements(By.className("android.widget.LinearLayout"));
        System.out.println("widgets count:" + widgets.size());
        logger.info("widgets count:" + widgets.size());
        boolean found = false;
        for (WebElement widget : widgets) {
            WebElement text = widget.findElement(By.className("android.widget.TextView"));
            System.out.println("text:" + text.getText());
            logger.info("text:" + text.getText());
        }

    }
}
