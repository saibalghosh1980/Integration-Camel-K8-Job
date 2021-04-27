package com.oup.k8job.camel.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component("springManagedSampleRoute")
public class SampleRoute extends RouteBuilder {
	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public void configure() throws Exception {
		

		onException(Exception.class).retryAttemptedLogLevel(LoggingLevel.WARN).logRetryAttempted(true)
				.maximumRedeliveries(0)
				.log(LoggingLevel.ERROR, logger,
						"${routeId} : Error encountered . The reason is ${exchangeProperty.CamelExceptionCaught} \\n\r\n"
								+ "					The stacktrace is ${exception.stacktrace}")

				.handled(true);

		// TODO Auto-generated method stub
		from("timer://foo?repeatCount=1").routeId("id_SampleRouteRoute")
		            .shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
					.onCompletion().log(LoggingLevel.INFO, logger, "*********** Ending the route **************")
					// .to("controlbus:route?async=false&action=stop&routeId=id_SampleRouteRoute")
					// .log("*********** id_SampleRouteRoute Stoped **************")
					.to("bean:springManagedShutdownManager?method=initiateShutdown()")
				.end()
				.log(LoggingLevel.INFO, logger, "Route started .....................................")
				// .to("stream:out");
				.to("bean:springManagedDeleteFilesBL?method=deleteOldFilesFolders()");
		
		from ("timer://foo1?repeatCount=1").routeId("id_SampleRoute_2")
		.to("controlbus:route?routeId=foo&action=stop&async=true")
		.delay(10000)
		.to("mock:result");

	}
}
