package com.briden.boot.controller;

import com.briden.boot.entity.jpa.Entry;
import com.briden.boot.repository.jpa.IEntryRepository;
import com.briden.boot.resources.DataResource;
import com.briden.boot.service.MultiDatasourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class AppController {

    @Autowired
    private IEntryRepository entryRepository;

    @Autowired
    private MultiDatasourceService multiDatasourceService;

    private Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping
    public List<Entry> dataEndpoint(@RequestParam Long companyId) {
        logger.info("Processing request for company {}", companyId);
        return entryRepository.findByCompanyId(companyId);
    }

    @PostMapping
    public void saveData(@RequestBody DataResource resource) {
        multiDatasourceService.saveEntries(resource);
    }

}
