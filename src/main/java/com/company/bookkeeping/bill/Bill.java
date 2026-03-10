package com.company.bookkeeping.bill;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Bill {
    private final Long id;
    private final String ownerName;
    private final String phone;
    private final String address;
    private final String city;
    private final String county;
    private final List<BillLine> lines;
    private final BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private final LocalDateTime createdAt;

    public Bill(Long id, String ownerName, String phone, String address, String city, String county,
                List<BillLine> lines, BigDecimal totalAmount, PaymentStatus paymentStatus, LocalDateTime createdAt) {
        this.id = id;
        this.ownerName = ownerName;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.county = county;
        this.lines = lines;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getOwnerName() { return ownerName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getCounty() { return county; }
    public List<BillLine> getLines() { return lines; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
