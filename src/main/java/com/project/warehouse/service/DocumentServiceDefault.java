package com.project.warehouse.service;

import com.project.warehouse.model.Document;
import com.project.warehouse.model.DocumentLine;
import com.project.warehouse.model.Item;
import com.project.warehouse.model.dto.CreateDocumentDTO;
import com.project.warehouse.model.dto.CreateDocumentLineDTO;
import com.project.warehouse.model.dto.UpdateDocumentDTO;
import com.project.warehouse.repository.DocumentLineRepository;
import com.project.warehouse.repository.DocumentRepository;
import com.project.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceDefault implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentLineRepository documentLineRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Override
    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    @Override
    public Document getOneById(long id) throws ChangeSetPersister.NotFoundException {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (documentOptional.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return documentOptional.get();
    }

    @Override
    public Document create(CreateDocumentDTO documentDTO) throws ChangeSetPersister.NotFoundException {
        Document document = Document.builder()
                .nr(documentDTO.getNr())
                .sender(documentDTO.getSender())
                .client(documentDTO.getClient())
                .type(documentDTO.getType())
                .build();
        document = documentRepository.save(document);

        List<DocumentLine> documentLines = new ArrayList<>();
        for (CreateDocumentLineDTO lineDTO : documentDTO.getLines()) {
            Optional<Item> itemOptional = itemRepository.findById(lineDTO.getItemId());
            if (itemOptional.isEmpty())
                throw new ChangeSetPersister.NotFoundException();
            Item item = itemOptional.get();
            documentLines.add(new DocumentLine(null, lineDTO.getQuantity(), item, document));
        }
        for (DocumentLine line : documentLines) {
            Item item = line.getItem();
            warehouseService.addItemQuantity(item.getId(), line.getQuantity(), document.getType());
        }

        documentLineRepository.saveAll(documentLines);
        return document;
    }

    @Override
    public Document update(long id, UpdateDocumentDTO updateDto) throws ChangeSetPersister.NotFoundException {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (documentOptional.isEmpty())
            throw new ChangeSetPersister.NotFoundException();
        Document document = documentOptional.get();
        document.setNr(updateDto.getNr());
        document.setSender(updateDto.getSender());
        document.setClient(updateDto.getClient());
        document.setType(updateDto.getType());
        return documentRepository.save(document);
    }

    @Override
    public void delete(long id) {
        Optional<Document> document = documentRepository.findById(id);
        document.ifPresent(value -> documentRepository.delete(value));
    }

    // TODO check this function
    @Override
    public Document addLine(long id, CreateDocumentLineDTO createDocumentLineDTO) throws ChangeSetPersister.NotFoundException {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (documentOptional.isEmpty())
            throw new ChangeSetPersister.NotFoundException();
        Document document = documentOptional.get();
        Optional<Item> item = itemRepository.findById(createDocumentLineDTO.getItemId());
        if (item.isEmpty())
            throw new ChangeSetPersister.NotFoundException();
        DocumentLine documentLine = new DocumentLine(null, createDocumentLineDTO.getQuantity(), item.get(), document);
        document.getDocumentLines().add(documentLine);
        return documentRepository.save(document);
    }
}
