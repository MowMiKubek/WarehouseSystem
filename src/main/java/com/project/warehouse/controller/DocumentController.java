package com.project.warehouse.controller;

import com.project.warehouse.model.Document;
import com.project.warehouse.model.dto.CreateDocumentDTO;
import com.project.warehouse.model.dto.CreateDocumentLineDTO;
import com.project.warehouse.model.dto.UpdateDocumentDTO;
import com.project.warehouse.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping
    public List<Document> getAll() {
        return documentService.getAll();
    }

    @GetMapping("/{id}")
    public Document getOneById(@PathVariable("id") long id) throws ChangeSetPersister.NotFoundException {
        return documentService.getOneById(id);
    }

    @PostMapping
    public Document create(@RequestBody CreateDocumentDTO createDocumentDTO) throws ChangeSetPersister.NotFoundException {
        return documentService.create(createDocumentDTO);
    }

    @PostMapping("/item/{id}")
    public Document addLine(@PathVariable("id") long id, @RequestBody CreateDocumentLineDTO createDocumentLineDTO) throws ChangeSetPersister.NotFoundException {
        return documentService.addLine(id, createDocumentLineDTO);
    }

    @PatchMapping("/{id}")
    public Document update(@PathVariable("id") long id, @RequestBody UpdateDocumentDTO updateDocumentDTO) throws ChangeSetPersister.NotFoundException {
        return documentService.update(id, updateDocumentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
