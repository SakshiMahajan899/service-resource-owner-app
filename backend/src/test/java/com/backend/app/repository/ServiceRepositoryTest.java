package com.backend.app.repository;

import com.backend.app.model.Service;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceRepositoryTest {

    @Test
    void testFindById_mocked() {
        // Arrange
        ServiceRepository repository = Mockito.mock(ServiceRepository.class);

        Service service = new Service();
        service.setId("test-id");


        Mockito.when(repository.findById("test-id")).thenReturn(Optional.of(service));

        // Act
        Optional<Service> result = repository.findById("test-id");

        // Assert
        assertTrue(result.isPresent());

    }

    @Test
    void testExistsById_mocked() {
        ServiceRepository repository = Mockito.mock(ServiceRepository.class);

        Mockito.when(repository.existsById("exists-id")).thenReturn(true);

        boolean exists = repository.existsById("exists-id");

        assertTrue(exists);
    }

    @Test
    void testDeleteById_mocked() {
        ServiceRepository repository = Mockito.mock(ServiceRepository.class);

        repository.deleteById("delete-id");

        Mockito.verify(repository).deleteById("delete-id");
    }
}
