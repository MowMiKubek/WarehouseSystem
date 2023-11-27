package com.project.warehouse.model.dto;

import com.project.warehouse.model.enums.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateDocumentDTO extends UpdateDocumentDTO {
    private List<CreateDocumentLineDTO> lines;

    public CreateDocumentDTO(String nr, String sender, String client, DocumentType type, List<CreateDocumentLineDTO> lines) {
        super(nr, sender, client, type);
        this.lines = lines;
    }
}
