package com.company.bookkeeping.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateItemRequest(
        @NotNull ItemType type,
        @NotBlank String specification,
        @NotNull @DecimalMin("0.0") BigDecimal unitPrice,
        @NotBlank String description
) {
}
