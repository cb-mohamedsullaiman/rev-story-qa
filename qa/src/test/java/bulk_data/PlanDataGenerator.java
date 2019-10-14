package test.java.bulk_data;

import com.chargebee.qa.apimethods.PlansAPI;
import com.chargebee.v2.internal.Request;
import com.chargebee.v2.models.Plan;
import com.chargebee.v2.Result;
import com.chargebee.v2.models.enums.PricingModel;
import main.java.constants.Currency;

public class PlanDataGenerator {



    /**
     * Creates an active plan
     */
    public Result createRecursiveTrialPlanWithActive(Integer trialDays, Integer price, String idSuffix) throws Exception{
        Plan.CreateRequest request = Plan.create()
                        .price(price)
                        .id((idSuffix.isEmpty() || idSuffix ==null) ? "active" :"active"+idSuffix)
                        .name("active"+idSuffix)
                        .pricingModel(PricingModel.FLAT_FEE)
                        .currencyCode(Currency.USD)
                        .periodUnit(Plan.PeriodUnit.MONTH)
                        .status(Plan.Status.ACTIVE)
                        ;
        if(trialDays>0){
             request.trialPeriod(trialDays);
        }
        Result result = request.request();
        return result;

    }





}
