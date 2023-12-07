package com.project.warehouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String brand;

    @Column
    private String name;

    @Column
    private String ean;

    @JsonBackReference
    @OneToMany(mappedBy = "item")
    private List<DocumentLine> documentLines;

    @JsonBackReference
    @OneToOne(mappedBy = "item")
    private Warehouse warehouse;
}

