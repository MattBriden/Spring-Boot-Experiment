package com.briden.boot.service;

import com.briden.boot.entity.Entry;
import com.briden.boot.repository.IEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CronJob {

    @Autowired
    private IEntryRepository entryRepository;

    private Logger logger = LoggerFactory.getLogger(CronJob.class);

    // runs every minute
    @Scheduled(cron = "0 * * * * *")
    public void updateEntryData() {
        logger.info("Updating data entry");
        Entry entry = new Entry();
        entry.setId(UUID.randomUUID().toString());
        entry.setCompanyid(1L);
        entryRepository.save(entry);
    }
}
