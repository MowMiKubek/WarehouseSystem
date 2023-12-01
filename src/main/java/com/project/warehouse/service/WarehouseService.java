package com.project.warehouse.service;

import com.project.warehouse.model.Warehouse;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface WarehouseService {
    Warehouse findByItemId(long id) throws ChangeSetPersister.NotFoundException;

    List<Warehouse> getAll();
}
