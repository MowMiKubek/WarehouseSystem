package com.project.warehouse.controller;

import com.project.warehouse.model.Warehouse;
import com.project.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAll() {
        List<Warehouse> allItems = warehouseService.getAll();
        return ResponseEntity.ok(allItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getOneByItemId(@PathVariable("id") long id) throws ChangeSetPersister.NotFoundException {
        Warehouse warehouseRecord = warehouseService.getItemById(id);
        return new ResponseEntity<>(warehouseRecord, HttpStatus.OK);
    }

}
