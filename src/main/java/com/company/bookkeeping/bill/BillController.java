package com.company.bookkeeping.bill;

import com.company.bookkeeping.common.AuthContext;
import com.company.bookkeeping.user.Permission;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillService billService;
    private final AuthContext authContext;

    public BillController(BillService billService, AuthContext authContext) {
        this.billService = billService;
        this.authContext = authContext;
    }

    @PostMapping
    public Bill create(@RequestHeader("Authorization") String authorizationHeader,
                       @Valid @RequestBody CreateBillRequest request) {
        authContext.requirePermission(authorizationHeader, Permission.MANAGE_BILLS);
        return billService.create(request);
    }

    @GetMapping
    public List<Bill> list(@RequestHeader("Authorization") String authorizationHeader) {
        authContext.requirePermission(authorizationHeader, Permission.MANAGE_BILLS);
        return billService.list();
    }
}
