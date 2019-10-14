package test.java.bulk_data;

import com.chargebee.qa.framework.core.Environment;
import com.chargebee.qa.pages.*;
import main.java.core.BaseTestClass;
import main.java.utils.CoreUtils;
import org.junit.Test;
import com.chargebee.Result;
import com.chargebee.models.Subscription;
import com.chargebee.models.enums.AutoCollection;
import test.java.constants.BulkDataConstants;
import test.java.constants.BulkDataConstants.*;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;


public class BulkDataGenerator  {

    SubscriptionDataGenerator subscriptionDataGenerator;
    SubHistoryGenerator subHistoryGenerator;

    public BulkDataGenerator() throws Exception{
        subscriptionDataGenerator = new SubscriptionDataGenerator();
        subHistoryGenerator = new SubHistoryGenerator();
    }

    public  void configureEnvironment() throws  Exception{
        CoreUtils.configureTestEnvironment();
    }

    public static void main(String args[])throws Exception{
        BulkDataGenerator bulkDataGenerator = new BulkDataGenerator();
        bulkDataGenerator.configureEnvironment();
        BulkDataGenScenario scenario = BulkDataGenScenario.BULK_SUB_HISTORIES;
        switch (scenario){
            case DIFF_STATES:
                bulkDataGenerator.createDataWithDifferentStates();
                break;
            case BULK_SUB_HISTORIES:
                bulkDataGenerator.createSubHistories();
                break;

        }
    }

    public void createDataWithDifferentStates() throws Exception{
        subscriptionDataGenerator.createDataWithDifferentStates();
    }

    public void createSubHistories() throws Exception{
        subHistoryGenerator.createSubHistory(BulkDataConstants.HISTORY_PRI_PLAN_COUNT,BulkDataConstants.HISTORY_SEC_PLAN_COUNT,BulkDataConstants.HISTORY_PLAN_SUFFIX,BulkDataConstants.HISTORY_SUB_COUNT,
                BulkDataConstants.HISTORY_SUB_SUFFIX, BulkDataConstants.HISTORY_UPGRADE_COUNT);
    }


    public enum BulkDataGenScenario{
        DIFF_STATES, BULK_SUB_HISTORIES
    }

}
