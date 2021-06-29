package com.briden.boot.integration;

import com.briden.boot.entity.dynamo.DynamoEntry;
import com.briden.boot.repository.dynamo.DynamoService;
import com.briden.boot.repository.jpa.IEntryRepository;
import com.briden.boot.resources.DataResource;
import com.briden.boot.service.MultiDatasourceService;
import org.hibernate.HibernateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

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
    public void testSavingMultipleDatasources_delete() {
        String id = UUID.randomUUID().toString();
        DataResource resource = new DataResource();
        resource.setId(id);
        resource.setCompanyId("2");

        doThrow(HibernateException.class).when(entryRepository).save(any());
        assertThrows(HibernateException.class,
                () -> multiDatasourceService.saveEntries(resource));

        DynamoEntry entry = new DynamoEntry();
        entry.setId(id);
        entry.setCompanyId("2");
        assertNull(dynamoRepository.getItemFromDynamo(entry));
    }

    @Test
    public void testSavingMultipleDatasources_rollback() {
        String id = UUID.randomUUID().toString();
        DataResource resource = new DataResource();
        resource.setId(id);
        resource.setCompanyId("3");

        DynamoEntry entry = new DynamoEntry();
        entry.setId(id);
        entry.setCompanyId("4");
        dynamoRepository.saveToDynamo(entry);

        doThrow(HibernateException.class).when(entryRepository).save(any());
        assertThrows(HibernateException.class,
                () -> multiDatasourceService.saveEntries(resource));

        assertNotNull(dynamoRepository.getItemFromDynamo(entry));
    }
}
