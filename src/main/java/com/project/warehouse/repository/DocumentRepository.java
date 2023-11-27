package com.project.warehouse.repository;

import com.project.warehouse.model.Document;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
