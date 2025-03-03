package com.dong.logistics.assistant.data;

import lombok.Data;
import java.time.LocalDate;

/**
 * @author ：dongyi
 * @description：TODO
 * @date ：2025/3/2 14:46
 */
@Data
public class Logistics {
    public String expressNumber;
    public ExpressName expressName;
    public String mobile;
    public LocalDate date;
    public Customer customer;
    public SignedStatus signedStatus;

    public Logistics(String expressNumber, ExpressName expressName, String mobile, LocalDate date, Customer customer, SignedStatus signedStatus) {
        this.expressNumber = expressNumber;
        this.expressName = expressName;
        this.mobile = mobile;
        this.date = date;
        this.customer = customer;
        this.signedStatus = signedStatus;
    }
}
