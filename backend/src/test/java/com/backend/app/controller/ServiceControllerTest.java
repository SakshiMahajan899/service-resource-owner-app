package com.backend.app.controller;

import com.backend.app.config.ServiceMapper;
import com.backend.app.dto.ServiceDto;
import com.backend.app.model.Service;
import com.backend.app.service.ServiceManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceManager serviceManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateService() throws Exception {
        Service service = new Service();
        Service created = new Service();
        ServiceDto dto = ServiceMapper.toDto(created);

        Mockito.when(serviceManager.createService(Mockito.any())).thenReturn(created);

        mockMvc.perform(post("/v1/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(service)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Service created successfully"));
    }

    @Test
    public void testUpdateService() throws Exception {
        String id = "123";
        Service updated = new Service();
        Service result = new Service();
        ServiceDto dto = ServiceMapper.toDto(result);

        Mockito.when(serviceManager.updateService(Mockito.eq(id), Mockito.any())).thenReturn(result);

        mockMvc.perform(put("/v1/services/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Service updated successfully"));
    }

    @Test
    public void testFetchService() throws Exception {
        String id = "456";
        Service service = new Service();
        ServiceDto dto = ServiceMapper.toDto(service);

        Mockito.when(serviceManager.getService(id)).thenReturn(service);

        mockMvc.perform(get("/v1/services/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Service fetched successfully"));
    }
}
