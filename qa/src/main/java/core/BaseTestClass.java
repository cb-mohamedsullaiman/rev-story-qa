package main.java.core;

import java.util.Locale;
import java.util.Random;

import com.chargebee.framework.apimethods.constants.CbTestConstants;
import com.chargebee.framework.pages.BasePage;
import com.chargebee.framework.pages.HomePage;
import com.chargebee.framework.webdriver.pom.AppTest;

public class BaseTestClass extends AppTest {

    public static String environment;
    BasePage basePage = new BasePage(getDriver());
    HomePage homePage = new HomePage(getDriver());

    public static String getTestEnvironment() {
        logger.info("" + System.getProperty("testenv", "cb local"));
        return System.getProperty("testenv", "cb local").toLowerCase(Locale.ENGLISH);
        // String env="stage";
        // return env;
    }

    public void login() {
        String url = "app.localcb.in:8080/login";
        String url1 = "http://" + url;
        basePage.loadURL(CbTestConstants.CB_URL);
        basePage.login(CbTestConstants.userName, CbTestConstants.password);
        basePage.selectSite("Test Site");
    }

    public void openURL(String URL) {
        basePage.loadURL(URL);
    }

    public String getAPIKey() {
        login();
        return basePage.APIKey();
    }

    public String getRanddomNumber() {
        Random random = new Random(System.nanoTime());
        int randomInt = random.nextInt(10000);
        String r= String.valueOf(randomInt);
//		System.out.println(randomInt);
        return r;
    }
}
