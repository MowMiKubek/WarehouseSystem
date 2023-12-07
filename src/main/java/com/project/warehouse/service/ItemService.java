package com.project.warehouse.service;

import com.project.warehouse.model.Item;
import com.project.warehouse.model.dto.CreateItemDTO;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.List;

public interface ItemService {
    List<Item> getAll();
    Item getItemByEan(String ean) throws NotFoundException;
    Item getItemById(long id) throws NotFoundException;
    Item save(CreateItemDTO createItemDTO);
    Item update(long id, Item item) throws NotFoundException;
    void delete(long id);
}
