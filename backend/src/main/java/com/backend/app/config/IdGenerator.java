package com.backend.app.config;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    public String generateServiceId(int index) {
        return "service_id_" + index;
    }

    public String generateResourceId(int serviceIndex, int resourceIndex) {
        return "resource_id_" + serviceIndex + "_" + resourceIndex;
    }

    public String generateOwnerId(int serviceIndex, int resourceIndex, int ownerIndex) {
        return "owner_id_" + serviceIndex + "_" + resourceIndex + "_" + ownerIndex;
    }
}

