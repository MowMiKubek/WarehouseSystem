package com.project.warehouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_body")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_item")
    private Item item;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_document")
    private Document document;
}
