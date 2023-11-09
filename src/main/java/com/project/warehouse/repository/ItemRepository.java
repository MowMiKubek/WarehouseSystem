package com.project.warehouse.repository;

import com.project.warehouse.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemByEan(String ean);
}
