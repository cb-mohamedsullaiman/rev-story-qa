package main.java.core;

import com.chargebee.qa.framework.core.Environment;
import main.java.utils.CoreUtils;

public class RevEnvironment extends Environment {

    public RevEnvironment() throws Exception{
        super(CoreUtils.getCoreProperties());
    }

}
