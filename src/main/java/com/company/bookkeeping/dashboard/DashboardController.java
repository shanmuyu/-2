package com.company.bookkeeping.dashboard;

import com.company.bookkeeping.common.AuthContext;
import com.company.bookkeeping.user.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final AuthContext authContext;

    public DashboardController(DashboardService dashboardService, AuthContext authContext) {
        this.dashboardService = dashboardService;
        this.authContext = authContext;
    }

    @GetMapping("/regions")
    public DashboardResponse stats(@RequestHeader("Authorization") String authorizationHeader,
                                   @RequestParam(defaultValue = "CITY") RegionLevel level) {
        authContext.requirePermission(authorizationHeader, Permission.VIEW_DASHBOARD);
        return dashboardService.calculate(level);
    }
}
