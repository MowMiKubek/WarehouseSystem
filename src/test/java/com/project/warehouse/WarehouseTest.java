package com.project.warehouse;

import com.project.warehouse.model.Warehouse;
import com.project.warehouse.service.WarehouseService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class WarehouseTest {
    @Autowired
    WarehouseService warehouseService;

    @Test
    void givenAllWarehouses_returnWarehouseList(){
        List<Warehouse> warehouseList =warehouseService.getAll();
        assertInstanceOf(List.class, warehouseList);
        assertEquals(4, warehouseList.size());
    }

    @Test
    void givenValidItemId_whenGetWarehouse_returnWarehouse() throws ChangeSetPersister.NotFoundException {
        Warehouse warehouse = warehouseService.getItemById(1);
        assertEquals(1, warehouse.getQuantity());
        assertEquals("Rimmel", warehouse.getItem().getBrand());
        assertEquals("Szminka", warehouse.getItem().getName());
        assertEquals("1234567890123", warehouse.getItem().getEan());
    }

    @Test
    void givenInvalidItemId_whenGetWarehouse_throwNotFoundException(){
        assertThrows(ChangeSetPersister.NotFoundException.class, () ->
                warehouseService.getItemById(1234));
    }
}
