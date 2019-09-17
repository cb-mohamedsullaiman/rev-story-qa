package test.java.bulk_data;

import com.chargebee.qa.apimethods.PlansAPI;
import com.chargebee.v2.models.Plan;
import com.chargebee.v2.Result;
import com.chargebee.v2.models.enums.PricingModel;
import main.java.constants.Currency;

public class PlanDataGenerator {



    /**
     * Creates an active plan
     */
    public void createRecursiveTrialPlanWithActive(Integer trialDays, Integer price) throws Exception{
        Result result = Plan.create()
                        .id("active")
                        .price(price)
                        .pricingModel(PricingModel.FLAT_FEE)
                        .currencyCode(Currency.USD)
                        .periodUnit(Plan.PeriodUnit.MONTH)
                        .trialPeriod(trialDays)
                        .status(Plan.Status.ACTIVE)
                        .request()
                        ;

    }





}
