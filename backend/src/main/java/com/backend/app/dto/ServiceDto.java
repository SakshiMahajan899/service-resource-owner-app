package com.backend.app.dto;

import lombok.Data;

import java.util.List;
@Data
public class ServiceDto {
    private String id;
    private List<ResourceDto> resources;
}
