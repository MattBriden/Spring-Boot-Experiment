package com.briden.boot.repository.jpa;

import com.briden.boot.entity.jpa.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEntryRepository extends CrudRepository<Entry, String> {

    List<Entry> findByCompanyId(Long companyId);
}
