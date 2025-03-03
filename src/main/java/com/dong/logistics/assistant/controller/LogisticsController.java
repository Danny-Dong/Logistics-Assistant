package com.dong.logistics.assistant.controller;

import com.dong.logistics.assistant.functions.LogisticsFunction;
import com.dong.logistics.assistant.services.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：Danny
 * @description：物流信息
 * @date ：2025/3/1 18:48
 */
@RestController
@CrossOrigin
public class LogisticsController {
    @Autowired
    private LogisticsService logisticsService;

    @CrossOrigin
    @GetMapping(value = "/logistics/list")
    public List<LogisticsFunction.LogisticsDetails> getLogistics() {
        return logisticsService.getLogistics();
    }
}
