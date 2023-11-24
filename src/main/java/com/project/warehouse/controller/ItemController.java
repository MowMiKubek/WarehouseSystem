package com.project.warehouse.controller;

import com.project.warehouse.model.Item;
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
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getSingleItem(@PathVariable("id") long id) throws NotFoundException {
        Item item = itemService.getSingleItem(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @GetMapping("/ean/{ean}")
    public ResponseEntity<Item> getItemByEan(@PathVariable("ean") String ean){
        Item item = itemService.getItemByEan(ean);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<Item> save(@RequestBody() Item item){
        Item item1 = itemService.save(item);
        return new ResponseEntity<>(item1, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Item> update(@RequestBody() Item item, @PathVariable("id") long id) throws NotFoundException {
        Item item1 = itemService.update(id, item);
        return new ResponseEntity<>(item1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id){
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
