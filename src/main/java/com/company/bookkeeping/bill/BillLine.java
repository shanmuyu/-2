package com.company.bookkeeping.bill;

import com.company.bookkeeping.item.MaterialItem;

import java.math.BigDecimal;

public record BillLine(MaterialItem item, int quantity, BigDecimal lineAmount) {
}
