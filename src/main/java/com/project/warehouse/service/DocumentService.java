package com.project.warehouse.service;

import com.project.warehouse.model.Document;
import com.project.warehouse.model.dto.CreateDocumentDTO;
import com.project.warehouse.model.dto.CreateDocumentLineDTO;
import com.project.warehouse.model.dto.UpdateDocumentDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface DocumentService {
    List<Document> getAll();
    Document getOneById(long id) throws ChangeSetPersister.NotFoundException;
    Document save(CreateDocumentDTO document) throws ChangeSetPersister.NotFoundException;
    Document update(long id, UpdateDocumentDTO updateDto) throws ChangeSetPersister.NotFoundException;
    void delete(long id);
    Document addLine(long id, CreateDocumentLineDTO createDocumentLineDTO) throws ChangeSetPersister.NotFoundException;
}
