package com.project.warehouse.service;

import com.project.warehouse.model.Item;
import com.project.warehouse.repository.ItemRepository;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemService{
    public List<Item> getAllItems();
    public Item getItemByEan(String ean);
    public Item getSingleItem(long id) throws NotFoundException;
    public Item save(Item item);
    public Item update(long id, Item item) throws NotFoundException;
    public void delete(long id);
}
