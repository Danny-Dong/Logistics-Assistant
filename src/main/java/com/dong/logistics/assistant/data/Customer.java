package com.dong.logistics.assistant.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Customer {
	private String name;
	private List<Logistics> logistics = new ArrayList<>();
}
