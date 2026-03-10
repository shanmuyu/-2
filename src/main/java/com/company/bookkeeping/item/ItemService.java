package com.company.bookkeeping.item;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ItemService {
    private final Map<Long, MaterialItem> items = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public MaterialItem create(CreateItemRequest request) {
        MaterialItem item = new MaterialItem(
                idGenerator.getAndIncrement(),
                request.type(),
                request.specification(),
                request.unitPrice(),
                request.description()
        );
        items.put(item.getId(), item);
        return item;
    }

    public MaterialItem require(Long id) {
        MaterialItem item = items.get(id);
        if (item == null) {
            throw new IllegalArgumentException("物品不存在: " + id);
        }
        return item;
    }

    public List<MaterialItem> list() {
        return new ArrayList<>(items.values());
    }
}
