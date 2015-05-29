/**
 * 
 */
package com.jabely.web.common.valves;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.service.pipeline.support.AbstractValveDefinitionParser;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.alibaba.citrus.turbine.util.TurbineUtil;

/**
 * @author mingxing.fmx
 * 
 */
public class ExceptionProcessValve extends AbstractValve {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExceptionProcessValve.class);

	@Autowired
	private HttpServletRequest request;

	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		TurbineRunDataInternal rundata = (TurbineRunDataInternal) TurbineUtil.getTurbineRunData(request);
		Exception exception = (Exception) pipelineContext.getAttribute("exception");
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		pw.close();
		rundata.getContext().put("exception", sw.toString());
		if (exception != null) {
			log.error("action或者screent出现异常(" + rundata.getTarget() + ")", exception);
			rundata.setTarget("error.vm");
		}
		pipelineContext.invokeNext();
	}

	public static class DefinitionParser extends AbstractValveDefinitionParser<ExceptionProcessValve> {
	}
}
