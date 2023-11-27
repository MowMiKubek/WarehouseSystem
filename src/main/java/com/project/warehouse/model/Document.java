package com.project.warehouse.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.warehouse.model.enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "document_header")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nr;

    @Column
    private String sender;

    @Column
    private String client;

    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @JsonManagedReference
    @OneToMany(mappedBy = "document")
    private List<DocumentLine> documentLines;
}
