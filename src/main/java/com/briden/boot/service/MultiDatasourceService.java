package com.briden.boot.service;

import com.briden.boot.config.DynamoTransactional;
import com.briden.boot.entity.dynamo.DynamoEntry;
import com.briden.boot.entity.jpa.Entry;
import com.briden.boot.repository.dynamo.DynamoService;
import com.briden.boot.repository.jpa.IEntryRepository;
import com.briden.boot.resources.DataResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MultiDatasourceService {

    @Autowired
    private IEntryRepository entryRepository;

    @Autowired
    private DynamoService dynamoEntryRepository;

    @Transactional
    @DynamoTransactional
    public void saveEntries(DataResource resource) {
        Entry entry = new Entry();
        entry.setCompanyId(Long.valueOf(resource.getCompanyId()));
        entry.setId(resource.getId());

        DynamoEntry dynamoEntry = new DynamoEntry();
        dynamoEntry.setCompanyId(resource.getCompanyId());
        dynamoEntry.setId(resource.getId());

        dynamoEntryRepository.saveToDynamo(dynamoEntry);
        entryRepository.save(entry);
    }
}
