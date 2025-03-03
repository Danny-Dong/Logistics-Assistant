package com.dong.logistics.assistant.functions;

import com.dong.logistics.assistant.data.ExpressName;
import com.dong.logistics.assistant.data.SignedStatus;
import com.dong.logistics.assistant.services.LogisticsService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;
import java.time.LocalDate;
import java.util.function.Function;

@Configuration
public class LogisticsFunction {

	private static final Logger logger = LoggerFactory.getLogger(LogisticsFunction.class);

	@Autowired
	private LogisticsService logisticsService;

	public record LogisticsDetailRequest(String expressNumber, String name) {
	}

	public record SignLogisticsRequest(String bookingNumber, String name) {
	}

	@JsonInclude(Include.NON_NULL)
	public record LogisticsDetails(String expressNumber, ExpressName expressName, String customerName,String mobile, LocalDate date, SignedStatus signedStatus) {
	}

	@Bean
	@Description("获取物流详情")
	public Function<LogisticsDetailRequest, LogisticsDetails> getLogisticsDetails() {
		return request -> {
			try {
				return logisticsService.getLogisticsDetails(request.expressNumber, request.name());
			} catch (Exception e) {
				logger.warn("logistic details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
				return new LogisticsDetails(request.expressNumber(), null, request.name(), null, null, null);
			}
		};
	}

	@Bean
	@Description("签收物流")
	public Function<SignLogisticsRequest, String> signLogistics() {
		return request -> {
			logisticsService.sigeLogistics(request.bookingNumber(), request.name());
			return "";
		};
	}

}
