package com.project.warehouse.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateItemDTO {
    private String brand;
    private String name;
    private String ean;
}
