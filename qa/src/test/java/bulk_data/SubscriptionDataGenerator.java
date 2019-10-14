package test.java.bulk_data;

import com.chargebee.qa.apimethods.SubscriptionsAPI;
import com.chargebee.v2.Result;
import com.chargebee.v2.models.Subscription;
import com.chargebee.v2.models.enums.AutoCollection;

public class SubscriptionDataGenerator {

    private SubscriptionsAPI subscriptionsAPI;

    public SubscriptionDataGenerator() throws Exception{
        subscriptionsAPI = new SubscriptionsAPI();
    }

    /**
     * Create subscription with In-trial State
     */
    public Result  createSubWithInTrialState() throws Exception{
       return subscriptionsAPI.createTrialSubscription();
    }

    public Result createSubWithActiveState() throws Exception{
        return subscriptionsAPI.createSubscription();
    }

    public Result createSubWithActiveState(String planId) throws Exception{
        return subscriptionsAPI.createSubscription(planId);
    }

    public void createDataWithDifferentStates() throws Exception{
        createSubWithInTrialState();
        createSubWithActiveState();
    }

    public void updateSubscription(String subId, String planId) throws Exception{
        subscriptionsAPI.updateSubscription(subId,planId);
    }


}
