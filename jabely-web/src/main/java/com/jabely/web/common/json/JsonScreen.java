/**
 * 
 */
package com.jabely.web.common.json;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;

/**
 * @author mingxing.fmx
 *
 */
public abstract class JsonScreen {
    public void execute(TurbineRunData rundata, Context context) {
        rundata.setRedirectTarget("/json/json.vm");
        context.put("object", getPageVO(rundata, context));
    }
    public abstract Object getPageVO(TurbineRunData rundata, Context context);
}
