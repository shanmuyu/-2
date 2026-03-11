package com.company.bookkeeping.dashboard;

import com.company.bookkeeping.bill.Bill;
import com.company.bookkeeping.bill.BillService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    private final BillService billService;

    public DashboardService(BillService billService) {
        this.billService = billService;
    }

    public DashboardResponse calculate(RegionLevel level) {
        Map<String, BigDecimal> amountByRegion = new HashMap<>();
        Map<String, Long> countByRegion = new HashMap<>();
        List<String> activeRegions = new ArrayList<>();

        for (Bill bill : billService.list()) {
            String region = level == RegionLevel.CITY ? bill.getCity() : bill.getCounty();
            amountByRegion.merge(region, bill.getTotalAmount(), BigDecimal::add);
            countByRegion.merge(region, 1L, Long::sum);
            activeRegions.add(bill.getCity() + "-" + bill.getCounty());
        }

        List<RegionStats> stats = amountByRegion.entrySet().stream()
                .map(entry -> new RegionStats(entry.getKey(), entry.getValue(), countByRegion.get(entry.getKey())))
                .sorted(Comparator.comparing(RegionStats::totalAmount).reversed())
                .toList();

        return new DashboardResponse(level, stats, activeRegions.stream().distinct().sorted().toList());
    }
}
