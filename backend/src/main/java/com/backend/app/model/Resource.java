package com.backend.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Resource {
    private String id;
    private List<Owner> owners;
}

