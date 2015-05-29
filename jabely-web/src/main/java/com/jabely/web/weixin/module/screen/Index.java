/**
 * 
 */
package com.jabely.web.weixin.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;

/**
 * @author xiedan
 *
 */
public class Index {
	
    public void execute(TurbineRunData rundata, Context context) {
    	context.put("name", "Hello World!");
    }
}
