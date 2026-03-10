package com.company.bookkeeping.item;

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
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;
    private final AuthContext authContext;

    public ItemController(ItemService itemService, AuthContext authContext) {
        this.itemService = itemService;
        this.authContext = authContext;
    }

    @PostMapping
    public MaterialItem create(@RequestHeader("Authorization") String authorizationHeader,
                               @Valid @RequestBody CreateItemRequest request) {
        authContext.requirePermission(authorizationHeader, Permission.MANAGE_ITEMS);
        return itemService.create(request);
    }

    @GetMapping
    public List<MaterialItem> list(@RequestHeader("Authorization") String authorizationHeader) {
        authContext.requirePermission(authorizationHeader, Permission.MANAGE_ITEMS);
        return itemService.list();
    }
}
