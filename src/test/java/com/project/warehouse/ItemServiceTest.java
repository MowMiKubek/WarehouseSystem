package com.project.warehouse;

import com.project.warehouse.model.Item;
import com.project.warehouse.model.dto.CreateItemDTO;
import com.project.warehouse.service.ItemServiceDefault;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemServiceDefault itemService;

    @Test
    void whenGetAllItems_returnsItemList() {
        List<Item> items = itemService.getAllItems();
        assertInstanceOf(List.class, items);
        assertEquals(8, items.size());
        assertEquals("Rimmel", items.get(0).getBrand());
        assertEquals("Bourjois", items.get(1).getBrand());
        assertEquals("Ecocera", items.get(2).getBrand());
    }

    @Test
    void givenValidEan_whenGetItemByEan_returnsItem() throws ChangeSetPersister.NotFoundException {
        Item item = itemService.getItemByEan("2345678901234");
        assertInstanceOf(Item.class, item);
        assertEquals("Bourjois", item.getBrand());
        assertEquals("Pomadka", item.getName());
        assertEquals("2345678901234", item.getEan());
    }

    @Test
    void givenInvalidEan_whenGetItemByEan_returnsNull() {
        assertThrows(ChangeSetPersister.NotFoundException.class,
                () -> itemService.getItemByEan("inexistingEan"));
    }

    @Test
    void givenValidId_whenGetSingleItem_returnsItem() throws ChangeSetPersister.NotFoundException {
        Item item = itemService.getSingleItem(1);
        assertInstanceOf(Item.class, item);
        assertEquals(1, item.getId());
        assertEquals("Rimmel", item.getBrand());
        assertEquals("Szminka", item.getName());
        assertEquals("1234567890123", item.getEan());
    }

    @Test
    void givenInvalidId_whenGetSingleItem_throwsNotFoundException() {
        assertThrows(ChangeSetPersister.NotFoundException.class,
                () -> itemService.getSingleItem(Integer.MAX_VALUE));
    }

    @Test
    void givenItem_thenCreateItem_returnItem() {
        CreateItemDTO itemData = new CreateItemDTO("Ecocera", "Puder", "123321123");
        Item item = itemService.save(itemData);
        assertEquals("Ecocera", item.getBrand());
        assertEquals("Puder", item.getName());
        assertEquals("123321123", item.getEan());
    }
}