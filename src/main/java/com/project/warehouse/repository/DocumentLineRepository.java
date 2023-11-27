package com.project.warehouse.repository;

import com.project.warehouse.model.DocumentLine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface DocumentLineRepository extends JpaRepository<DocumentLine, Long> {
}
