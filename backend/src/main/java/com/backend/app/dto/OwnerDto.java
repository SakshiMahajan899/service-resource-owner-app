package com.backend.app.dto;

import lombok.Data;

@Data
public class OwnerDto {
    private String id;
    private String name;
    private String accountNumber;
    private int level;
}
