package com.project.warehouse.service;

import com.project.warehouse.model.Warehouse;
import com.project.warehouse.model.enums.DocumentType;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface WarehouseService {
    Warehouse findByItemId(long id) throws ChangeSetPersister.NotFoundException;

    void addItemQuantity(long itemId, int quantity, DocumentType type) throws ChangeSetPersister.NotFoundException;

    List<Warehouse> getAll();
}
