package test.java;

import com.chargebee.qa.framework.core.Environment;
import com.chargebee.qa.pages.*;
import main.java.core.BaseTestClass;
import org.junit.Test;
import com.chargebee.Result;
import com.chargebee.models.Subscription;
import com.chargebee.models.enums.AutoCollection;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;

public class BulkDataGenerator  {


    public static void subscription() throws Exception{
        Result result = Subscription.create()
                .planId("basic")
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john@user.com")
                .billingAddressFirstName("John")
                .billingAddressLastName("Doe")
                .billingAddressLine1("PO Box 9999")
                .billingAddressCity("Walnut")
                .billingAddressState("California")
                .billingAddressZip("91789")
                .billingAddressCountry("US")
                .request();
    }

    public static void configureEnvironment() throws  Exception{
//        Properties siteProperties = new Properties();
//        siteProperties.load(new FileInputStream("src/main/resources/site-meta.properties"));
//        Properties envProperties = new Properties();
//        envProperties.load(new FileInputStream("src/main/resources/environment.properties"));
//        String env = envProperties.getProperty("env","local");
//        String domainSuffix;
//        if(env.equalsIgnoreCase("dev")){
//            domainSuffix = envProperties.getProperty("dev.app.domain-suffix");
//        }else if (env.equalsIgnoreCase("staging")){
//            domainSuffix = envProperties.getProperty("staging.app.domain-suffix");
//        }else if(env.equalsIgnoreCase("prod")){
//            domainSuffix = envProperties.getProperty("prod.app.domain-suffix");
//        }else{
//            domainSuffix = envProperties.getProperty("local.app.domain-suffix")+":"+
//                    envProperties.getProperty("local.app.port");
//        }
//        overrideProtocol(domainSuffix);
//        Environment.configure(siteProperties.getProperty("site.name")+"-test",
//                siteProperties.getProperty("site.test.api-key"));



    }

    public static void main(String args[])throws Exception{
        
    }
    

}
