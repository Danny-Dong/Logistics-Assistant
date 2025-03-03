package com.dong.logistics.assistant.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：dongyi
 * @description：TODO
 * @date ：2025/3/2 14:53
 */
@Data
public class LogisticsData {
    private List<Customer> customers = new ArrayList<>();
    private List<Logistics> logistics = new ArrayList<>();
}
