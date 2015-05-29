/**
 * 
 */
package com.jabely.web.common.valves;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.jabely.web.common.UserContext;

/**
 * @author fangmingxing
 *
 */
public class UserContextCleanValve extends AbstractValve {
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		UserContext.removeUserSessionInfo();
	}
}
