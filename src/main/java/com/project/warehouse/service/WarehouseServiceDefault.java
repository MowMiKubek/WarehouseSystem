package com.project.warehouse.service;

import com.project.warehouse.model.Warehouse;
import com.project.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceDefault implements WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    @Override
    public Warehouse findByItemId(long id) throws ChangeSetPersister.NotFoundException {
        return warehouseRepository.findByItem_Id(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }
}