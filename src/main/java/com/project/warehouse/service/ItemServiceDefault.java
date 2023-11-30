package com.project.warehouse.service;

import com.project.warehouse.model.Item;
import com.project.warehouse.model.dto.CreateItemDTO;
import com.project.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceDefault implements ItemService {
    @Autowired
    ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemByEan(String ean) throws ChangeSetPersister.NotFoundException {
        return itemRepository.findItemByEan(ean)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Item getSingleItem(long id) throws ChangeSetPersister.NotFoundException {
        return itemRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Item save(CreateItemDTO createItemDTO) {
        Item item = new Item(0, createItemDTO.getBrand(), createItemDTO.getName(), createItemDTO.getEan(), null);
        return itemRepository.save(item);
    }

    public Item update(long id, Item item) throws ChangeSetPersister.NotFoundException {
        Item item1 = itemRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setName(item.getName());
                    existingItem.setEan(item.getEan());
                    return existingItem;
                })
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return itemRepository.save(item1);
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }
}
