package com.company.bookkeeping.dashboard;

import java.util.List;

public record DashboardResponse(RegionLevel level, List<RegionStats> regionAmount, List<String> activeRegions) {
}
