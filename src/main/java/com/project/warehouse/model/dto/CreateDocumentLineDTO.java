package com.project.warehouse.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDocumentLineDTO {
    private int quantity;
    private long itemId;
}
