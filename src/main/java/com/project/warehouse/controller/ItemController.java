package com.project.warehouse.controller;

import com.project.warehouse.model.Item;
import com.project.warehouse.model.dto.CreateItemDTO;
import com.project.warehouse.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        List<Item> items = itemService.getAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getOneById(@PathVariable("id") long id) throws NotFoundException {
        Item item = itemService.getItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/ean/{ean}")
    public ResponseEntity<Item> getOneByEan(@PathVariable("ean") String ean) throws NotFoundException {
        Item item = itemService.getItemByEan(ean);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Item> save(@RequestBody CreateItemDTO createItemDTO) {
        Item item1 = itemService.save(createItemDTO);
        return new ResponseEntity<>(item1, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Item> update(@RequestBody Item item, @PathVariable("id") long id) throws NotFoundException {
        Item item1 = itemService.update(id, item);
        return new ResponseEntity<>(item1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
