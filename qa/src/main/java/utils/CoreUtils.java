package main.java.utils;

import com.chargebee.qa.framework.core.Environment;
import main.java.constants.DirConstants;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileFilter;
import java.util.Properties;


public class CoreUtils {

    public static void configureEnvironment() throws Exception{
        System.setProperty("env.class","main.java.core.RevEnvironment");

    }

    public static Properties getCoreProperties() throws Exception{
        File propDir = new File(DirConstants.MAIN_PROPERTIES);
        return PropUtils.loadAllProperties(propDir);

    }
}
