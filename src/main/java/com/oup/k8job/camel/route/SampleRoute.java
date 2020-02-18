package com.oup.k8job.camel.route;

import org.springframework.stereotype.Component;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;

@Component("springManagedSampleRoute")
public class SampleRoute extends RouteBuilder {
	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	

	@Override
	public void configure() throws Exception {
		onCompletion()		
        .log(LoggingLevel.INFO,logger,"*********** Ending the route **************")
        //.to("controlbus:route?async=false&action=stop&routeId=id_SampleRouteRoute")
        //.log("*********** id_SampleRouteRoute Stoped **************")
        .to("bean:springManagedShutdownManager?method=initiateShutdown()");
		
		// TODO Auto-generated method stub
		from("timer://foo?repeatCount=1").routeId("id_SampleRouteRoute")
		.log(LoggingLevel.INFO,logger,
				"Route started .....................................")
		.setBody(simple("*********************** Hello World!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"))
		.to("stream:out");
		
	}
}
