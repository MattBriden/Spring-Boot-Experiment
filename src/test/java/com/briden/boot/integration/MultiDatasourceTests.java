package com.briden.boot.integration;

import com.briden.boot.entity.dynamo.DynamoEntry;
import com.briden.boot.repository.dynamo.DynamoService;
import com.briden.boot.repository.jpa.IEntryRepository;
import com.briden.boot.resources.DataResource;
import com.briden.boot.service.MultiDatasourceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class MultiDatasourceTests extends BaseIntegrationTest {

    @Autowired
    private MultiDatasourceService multiDatasourceService;

    @Autowired
    private DynamoService dynamoRepository;

    @MockBean
    private IEntryRepository entryRepository;

    @Test
    public void testSavingMultipleDatasources() {
        String id = UUID.randomUUID().toString();
        DataResource resource = new DataResource();
        resource.setId(id);
        resource.setCompanyId("1");

        multiDatasourceService.saveEntries(resource);

        DynamoEntry entry = new DynamoEntry();
        entry.setId(id);
        entry.setCompanyId("1");
        DynamoEntry savedEntry = dynamoRepository.getItemFromDynamo(entry);

        assertNotNull(savedEntry);
    }

    @Test
    public void testSavingMultipleDatasources_rollback() {
        String id = UUID.randomUUID().toString();
        DataResource resource = new DataResource();
        resource.setId(id);
        resource.setCompanyId("2");

        doThrow(RuntimeException.class).when(entryRepository).save(any());


        assertThrows(RuntimeException.class,
                () -> multiDatasourceService.saveEntries(resource));

        DynamoEntry entry = new DynamoEntry();
        entry.setId(id);
        entry.setCompanyId("2");
        assertNull(dynamoRepository.getItemFromDynamo(entry));
    }
}
