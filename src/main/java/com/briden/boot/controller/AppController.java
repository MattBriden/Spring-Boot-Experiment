package com.briden.boot.controller;

import com.briden.boot.entity.Entry;
import com.briden.boot.repository.IEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class AppController {

    @Autowired
    private IEntryRepository entryRepository;

    @GetMapping
    public List<Entry> dataEndpoint(@RequestParam Long companyId) {
        return entryRepository.findByCompanyId(companyId);
    }

}
