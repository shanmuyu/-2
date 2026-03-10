package com.company.bookkeeping.bill;

import com.company.bookkeeping.item.ItemService;
import com.company.bookkeeping.item.MaterialItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BillService {
    private final ItemService itemService;
    private final Map<Long, Bill> bills = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BillService(ItemService itemService) {
        this.itemService = itemService;
    }

    public Bill create(CreateBillRequest request) {
        List<BillLine> lines = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (BillLineRequest lineRequest : request.lines()) {
            MaterialItem item = itemService.require(lineRequest.itemId());
            BigDecimal lineAmount = item.getUnitPrice().multiply(BigDecimal.valueOf(lineRequest.quantity()));
            lines.add(new BillLine(item, lineRequest.quantity(), lineAmount));
            total = total.add(lineAmount);
        }

        Bill bill = new Bill(
                idGenerator.getAndIncrement(),
                request.ownerName(),
                request.phone(),
                request.address(),
                request.city(),
                request.county(),
                lines,
                total,
                request.paymentStatus(),
                LocalDateTime.now()
        );
        bills.put(bill.getId(), bill);
        return bill;
    }

    public List<Bill> list() {
        return new ArrayList<>(bills.values());
    }
}
