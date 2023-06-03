package com.rapitskyi.carsharing.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddInquireRequest {
    @NotBlank
    String carType;
    @NotBlank
    String description;

    boolean needsDelivery;
}
