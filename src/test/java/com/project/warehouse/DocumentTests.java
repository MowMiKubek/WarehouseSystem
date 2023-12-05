package com.project.warehouse;

import com.project.warehouse.model.Document;
import com.project.warehouse.model.Warehouse;
import com.project.warehouse.model.dto.CreateDocumentDTO;
import com.project.warehouse.model.dto.CreateDocumentLineDTO;
import com.project.warehouse.model.enums.DocumentType;
import com.project.warehouse.service.DocumentService;
import com.project.warehouse.service.WarehouseService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DocumentTests {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private WarehouseService warehouseService;

    @Test
    public void getAllDocuments_returnsDocumentList() {
        List<Document> documents = documentService.getAll();
        assertInstanceOf(List.class, documents);
        assertEquals(2, documents.size());
        assertEquals("123", documents.get(0).getNr());
        assertEquals("124", documents.get(1).getNr());
    }

    @Test
    public void givenValidId_whenGetDocument_returnsDocument() throws ChangeSetPersister.NotFoundException {
        Document document = documentService.getOneById(1);
        assertEquals("123", document.getNr());
        assertEquals("Kosmetix", document.getSender());
        assertEquals("Joanna Nowak", document.getClient());
        assertEquals(DocumentType.PZ, document.getType());
    }

    @Test
    public void givenInvalidId_whenGetDocuments_throwNotFoundException() {
        assertThrows(ChangeSetPersister.NotFoundException.class, () ->
                documentService.getOneById(123));
    }

    @Test
    public void givenDocumentDTO_whenCreateDocument_returnsDocument() throws ChangeSetPersister.NotFoundException {
        List<CreateDocumentLineDTO> documentLines = new ArrayList<>();
        documentLines.add(new CreateDocumentLineDTO(1, 1));
        CreateDocumentDTO documentDTO =
                new CreateDocumentDTO("1234", "Sender1", "Client1", DocumentType.PZ, documentLines);
        Document document = documentService.save(documentDTO);
        assertEquals("1234", document.getNr());
        assertEquals("Sender1", document.getSender());
        assertEquals("Client1", document.getClient());
        assertEquals(DocumentType.PZ, document.getType());
    }

    @Test
    public void givenDocumentDTOWithTypePZ_when_createDocuments_UpdatesWarehouse() throws ChangeSetPersister.NotFoundException {
        List<CreateDocumentLineDTO> documentLines = new ArrayList<>();
        documentLines.add(new CreateDocumentLineDTO(5, 1));
        CreateDocumentDTO documentDTO =
                new CreateDocumentDTO("1234", "Sender1", "Client1", DocumentType.PZ, documentLines);
        documentService.save(documentDTO);
        Warehouse warehouseRecord = warehouseService.getItemById(1);
        assertEquals(6, warehouseRecord.getQuantity());
    }

    @Test
    public void givenDocumentDTOWithTypeWZ_when_createDocuments_UpdatesWarehouse() throws ChangeSetPersister.NotFoundException {
        List<CreateDocumentLineDTO> documentLines = new ArrayList<>();
        documentLines.add(new CreateDocumentLineDTO(3, 3));
        CreateDocumentDTO documentDTO =
                new CreateDocumentDTO("1234", "Sender1", "Client1", DocumentType.WZ, documentLines);
        documentService.save(documentDTO);
        Warehouse warehouseRecord = warehouseService.getItemById(3);
        assertEquals(2, warehouseRecord.getQuantity());
    }
}
