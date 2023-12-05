package com.project.warehouse.model.dto;

import com.project.warehouse.model.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDocumentDTO {
    private String nr;
    private String sender;
    private String client;
    private DocumentType type;
}
