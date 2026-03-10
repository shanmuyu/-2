package com.company.bookkeeping.bill;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateBillRequest(
        @NotBlank String ownerName,
        @NotBlank String phone,
        @NotBlank String address,
        @NotBlank String city,
        @NotBlank String county,
        @NotEmpty List<@Valid BillLineRequest> lines,
        @NotNull PaymentStatus paymentStatus
) {
}
