package com.xxl.job.core.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * glue job handler
 *
 * @author xuxueli 2016-5-19 21:05:45
 */
@Component
public class DemoJobHandler extends IJobHandler {

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("----------- XXL-JOB , Hello World -----------");
		return ReturnT.SUCCESS;
	}

}
