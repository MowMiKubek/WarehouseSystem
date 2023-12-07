package com.project.warehouse.service;

import com.project.warehouse.model.Item;
import com.project.warehouse.model.Warehouse;
import com.project.warehouse.model.enums.DocumentType;
import com.project.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Service
public class WarehouseServiceDefault implements WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ItemService itemService;

    @Override
    public Warehouse getItemById(long id) throws NotFoundException {
        return warehouseRepository.findByItem_Id(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void addItemQuantity(long itemId, int quantity, DocumentType type) throws NotFoundException {
        Optional<Warehouse> warehouseRecord = warehouseRepository.findByItem_Id(itemId);
        Item item = itemService.getItemById(itemId);
        if (warehouseRecord.isEmpty()) {
            Warehouse newRecord = new Warehouse(0, quantity, item);
            warehouseRepository.save(newRecord);
        } else {
            Warehouse record = warehouseRecord.get();
            long newQuantity = record.getQuantity();
            if(type == DocumentType.PZ)
                newQuantity += quantity;
            else
                newQuantity -= quantity;
            if (newQuantity >= 0) {
                record.setQuantity(newQuantity);
            }
            warehouseRepository.save(record);
        }
    }

    @Override
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }
}