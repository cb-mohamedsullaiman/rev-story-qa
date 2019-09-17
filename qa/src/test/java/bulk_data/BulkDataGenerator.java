package test.java.bulk_data;

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

    SubscriptionDataGenerator subscriptionDataGenerator;

    public BulkDataGenerator() throws Exception{
        subscriptionDataGenerator = new SubscriptionDataGenerator();
    }

    public  void configureEnvironment() throws  Exception{
        CoreUtils.configureTestEnvironment();
    }

    public static void main(String args[])throws Exception{
        BulkDataGenerator bulkDataGenerator = new BulkDataGenerator();
        bulkDataGenerator.configureEnvironment();
        BulkDataGenScenario scenario = BulkDataGenScenario.DIFF_STATES;
        switch (scenario){
            case DIFF_STATES:
                bulkDataGenerator.createDataWithDifferentStates();
        }
    }

    public void createDataWithDifferentStates() throws Exception{
        subscriptionDataGenerator.createDataWithDifferentStates();
    }



    public enum BulkDataGenScenario{
        DIFF_STATES
    }

}
