package com.briden.boot.repository;

import com.briden.boot.entity.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEntryRepository extends CrudRepository<Entry, String> {

    List<Entry> findByCompanyId(Long companyId);
}
