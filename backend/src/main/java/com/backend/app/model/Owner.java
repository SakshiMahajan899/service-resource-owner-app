package com.backend.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Owner {
    private String id;
    private String name;
    private String accountNumber;
    private int level;
}

