package com.backend.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "services")
public class Service {
    @Id
    private String id;
    private List<Resource> resources;
}
