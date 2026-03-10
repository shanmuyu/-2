package com.company.bookkeeping.dashboard;

import java.math.BigDecimal;

public record RegionStats(String region, BigDecimal totalAmount, long billCount) {
}
