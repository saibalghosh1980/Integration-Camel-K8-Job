package com.oup.k8job.shutdownmanager;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("springManagedShutdownManager")
class ShutdownManager {
	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private CamelContext camelContext;

	public void initiateShutdown() {
		logger.debug("Stopping camel context");
		camelContext.getShutdownStrategy().setLogInflightExchangesOnTimeout(true);
		camelContext.getShutdownStrategy().setTimeUnit(TimeUnit.MINUTES);
		camelContext.getShutdownStrategy().setTimeout(120);
		camelContext.stop();
		logger.debug("Stopping camel context finished");
		logger.debug("Stopping Spring BOOT Application --> "+appContext.getId());
		SpringApplication.exit(appContext, () -> 0);
	}
}
