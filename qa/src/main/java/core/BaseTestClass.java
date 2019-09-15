package main.java.core;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import com.chargebee.qa.framework.core.Environment;
import com.chargebee.qa.apimethods.constants.CbTestConstants;
import com.chargebee.qa.pages.BasePage;
import com.chargebee.qa.pages.HomePage;
import com.chargebee.qa.framework.webdriver.pom.AppTest;
import main.java.utils.CoreUtils;
import org.junit.BeforeClass;


public class BaseTestClass extends AppTest {

    @BeforeClass
    public void configureEnvironment() throws  Exception{
        CoreUtils.configureEnvironment();
    }

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
