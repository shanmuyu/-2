package com.company.bookkeeping.item;

import java.math.BigDecimal;

public class MaterialItem {
    private final Long id;
    private ItemType type;
    private String specification;
    private BigDecimal unitPrice;
    private String description;

    public MaterialItem(Long id, ItemType type, String specification, BigDecimal unitPrice, String description) {
        this.id = id;
        this.type = type;
        this.specification = specification;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public String getSpecification() {
        return specification;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getDescription() {
        return description;
    }
}
