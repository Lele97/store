package com.webapp.enterprise.intern.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String status;
    private int quantity;
    private Long userId;
    private Long productID;
}
