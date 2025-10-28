package com.suarez.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review_data")
public class ReviewData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_data_seq")
    @SequenceGenerator(name = "review_data_seq", sequenceName = "review_data_SEQ", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductData product;

    private String author;

    private Integer rating; // 1-5

    @Column(length = 2000)
    private String comment;

    private LocalDateTime created;
}
