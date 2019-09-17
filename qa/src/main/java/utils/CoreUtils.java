package main.java.utils;

import com.chargebee.qa.framework.core.Environment;
import main.java.constants.DirConstants;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileFilter;
import java.util.Properties;


public class CoreUtils {

    public static Environment configureEnvironment() throws Exception{
        System.setProperty("env.class","main.java.core.RevEnvironment");
        return Environment.inst;

    }

    public static void  configureTestEnvironment() throws Exception{
        configureEnvironment();
        String testSiteName = Environment.getSiteProperty().getTestSiteName();
        String testApiKey = Environment.getSiteProperty().getTestApiKey();

        com.chargebee.Environment.configure(testSiteName,testApiKey);
        com.chargebee.v2.Environment.configure(testSiteName,testApiKey);

    }

    public static Properties getCoreProperties() throws Exception{
        File propDir = new File(DirConstants.MAIN_PROPERTIES);
        return PropUtils.loadAllProperties(propDir);

    }
}
