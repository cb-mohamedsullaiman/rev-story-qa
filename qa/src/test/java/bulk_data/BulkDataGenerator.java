package test.java;

import com.chargebee.qa.framework.core.Environment;
import com.chargebee.qa.pages.*;
import main.java.core.BaseTestClass;
import main.java.utils.CoreUtils;
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
        CoreUtils.configureEnvironment();
    }

    public static void main(String args[])throws Exception{
        configureEnvironment();
        subscription();
    }
    

}
