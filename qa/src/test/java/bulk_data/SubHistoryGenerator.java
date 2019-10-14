package test.java.bulk_data;

import com.chargebee.qa.apimethods.SubscriptionsAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.chargebee.v2.Result;
import test.java.constants.BulkDataConstants;

public class SubHistoryGenerator {

    private SubscriptionsAPI subscriptionsAPI;

    private List<String> primaryPlans;

    private List<String> secondaryPlans;

    private int updateCount;

    private List<String> subs;

    public SubHistoryGenerator(){
        subscriptionsAPI = new SubscriptionsAPI();
        primaryPlans = new ArrayList<>();
        secondaryPlans = new ArrayList<>();
        subs = new ArrayList<>();
        updateCount = 0;
    }

    public void createRequiredPlans(Integer primaryPlanCount,  Integer secondaryPlanCount, String idSuffix) throws Exception{
        PlanDataGenerator planDataGenerator = new PlanDataGenerator();
        int i=0;
        while(i < primaryPlanCount){
            Result result = planDataGenerator.createRecursiveTrialPlanWithActive(0,i*2 * 10000, idSuffix+(i*2));
            primaryPlans.add(result.plan().id());
            i++;
        }
        i=0;
        while(i < secondaryPlanCount){
            Result result = planDataGenerator.createRecursiveTrialPlanWithActive(0,((i*2)+1) * 10000, idSuffix+((i*2)+1));
            secondaryPlans.add(result.plan().id());
            i++;
        }

    }



    public void createSubscriptions(Integer subCount, String idSuffix) throws Exception{
        SubscriptionDataGenerator subscriptionDataGenerator = new SubscriptionDataGenerator();
        int i=0;
        Random rand = new Random();
        while(i < subCount){
            Result result = subscriptionDataGenerator.createSubWithActiveState(primaryPlans.get(rand.nextInt(primaryPlans.size())));
            subs.add(result.subscription().id());
            i++;
        }
    }

    public void updateAllSubscriptions(Integer maxUpdateCount) throws Exception{
        SubscriptionDataGenerator subscriptionDataGenerator = new SubscriptionDataGenerator();
        Random rand = new Random();
        List<String> plans;
        Thread.sleep(BulkDataConstants.ETL_SYNC_TIME_SEC);
        while(updateCount < maxUpdateCount) {
            if (updateCount % 2 == 0) {
                plans = secondaryPlans;
            } else {
                plans = primaryPlans;
            }
            int planSize = plans.size();
            for (String subId : subs) {
                subscriptionDataGenerator.updateSubscription(subId, plans.get(rand.nextInt(planSize)));
            }
            updateCount++;
        }

    }

    public void createSubHistory(Integer primaryPlanCount, Integer secondaryPlanCount, String planIdSuffix,
                                 Integer subCount, String subIdSuffix, Integer maxUpdateCount) throws Exception{
        createRequiredPlans(primaryPlanCount,secondaryPlanCount, planIdSuffix);
        createSubscriptions(subCount, subIdSuffix);
        updateAllSubscriptions(maxUpdateCount);
    }

}
