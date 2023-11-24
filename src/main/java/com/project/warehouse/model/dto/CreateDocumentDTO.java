package com.project.warehouse.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateDocumentDTO extends UpdateDocumentDTO {
    private List<CreateDocumentLineDTO> lines;
}
