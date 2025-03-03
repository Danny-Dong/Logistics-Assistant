package com.dong.logistics.assistant.services;

import com.dong.logistics.assistant.data.*;
import com.dong.logistics.assistant.functions.LogisticsFunction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LogisticsService {

	private final LogisticsData db;

	public LogisticsService() {
		db = new LogisticsData();
		initLocalData();
	}

	private void initLocalData() {
		List<String> names = List.of("张顺", "燕小乙", "卢俊义", "张青", "花荣");
		List<String> mobiles = List.of("13812345678", "13812345679", "13812345670", "13812345671", "13812345672");
		List<String> expressNumbers = List.of("CA1234567890", "CA1234567891", "CA1234567892", "CA1234567893", "CA1234567894");
		Set<String> phoneNumbers = new HashSet<>();
		Set<String> waybillNumbers = new HashSet<>();
		Random random = new Random();

		var customers = new ArrayList<Customer>();
		var logistics = new ArrayList<Logistics>();

		for (int i = 0; i < 5; i++) {
			String name = names.get(i);
			ExpressName expressName = ExpressName.values()[random.nextInt(ExpressName.values().length)];
			Customer customer = new Customer();
			customer.setName(name);
			LocalDate date = LocalDate.now().plusDays(2 * (i + 1));

			Logistics logistic = new Logistics(expressNumbers.get(i), expressName, mobiles.get(i), date, customer, SignedStatus.未签收);

			customer.getLogistics().add(logistic);
			customers.add(customer);
			logistics.add(logistic);
		}
		// Reset the database on each start
		db.setCustomers(customers);
		db.setLogistics(logistics);
	}

	// 获取所以的物流信息列表
	public List<LogisticsFunction.LogisticsDetails> getLogistics() {
		return db.getLogistics().stream().map(this::toLogisticsDetails).toList();
	}

	// 根据姓名或手机号查物流
	private Logistics findLogistics(String expressNumber, String name) {
		return db.getLogistics()
				.stream()
				.filter(b -> b.expressNumber.equalsIgnoreCase(expressNumber))
				.filter(b -> b.getCustomer().getName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Logistics not found"));
	}

	// 查询航班详情
	public LogisticsFunction.LogisticsDetails getLogisticsDetails(String expressNumber, String name) {
		var booking = findLogistics(expressNumber, name);
		return toLogisticsDetails(booking);
	}

	// 签收物流
	public void sigeLogistics(String expressNumber, String name) {
		var logistics = findLogistics(expressNumber, name);
		if (logistics.getDate().isBefore(LocalDate.now().plusDays(2))) {
			throw new IllegalArgumentException("Logistics cannot be sign within 48 hours of the start date.");
		}
		logistics.setSignedStatus(SignedStatus.已签收);
	}

	private LogisticsFunction.LogisticsDetails toLogisticsDetails(Logistics logistics) {
		return new LogisticsFunction.LogisticsDetails(logistics.expressNumber, logistics.expressName, logistics.getCustomer().getName(),
				logistics.mobile, logistics.getDate(), logistics.signedStatus);
	}
}
