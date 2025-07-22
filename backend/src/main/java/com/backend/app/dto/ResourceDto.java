package com.backend.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResourceDto {
    private String id;
    private List<OwnerDto> owners;
}
