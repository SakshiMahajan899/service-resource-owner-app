package com.backend.app.config;

import com.backend.app.model.Service;
import com.backend.app.model.Resource;
import com.backend.app.model.Owner;
import com.backend.app.dto.ServiceDto;
import com.backend.app.dto.ResourceDto;
import com.backend.app.dto.OwnerDto;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceMapper {

    public static ServiceDto toDto(Service service) {
        if (service == null) return null;

        ServiceDto dto = new ServiceDto();
        dto.setId(service.getId());
        dto.setResources(mapResources(service.getResources()));
        return dto;
    }

    private static List<ResourceDto> mapResources(List<Resource> resources) {
        if (resources == null) return null;

        return resources.stream().map(resource -> {
            ResourceDto dto = new ResourceDto();
            dto.setId(resource.getId());
            dto.setOwners(mapOwners(resource.getOwners()));
            return dto;
        }).collect(Collectors.toList());
    }

    private static List<OwnerDto> mapOwners(List<Owner> owners) {
        if (owners == null) return null;

        return owners.stream().map(owner -> {
            OwnerDto dto = new OwnerDto();
            dto.setName(owner.getName());
            dto.setLevel(owner.getLevel());
            dto.setAccountNumber(owner.getAccountNumber());
            dto.setId(owner.getId());
            return dto;
        }).collect(Collectors.toList());
    }
}
