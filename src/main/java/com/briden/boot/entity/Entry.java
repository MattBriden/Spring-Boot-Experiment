package com.briden.boot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Entry {

    @Id
    private String id;

    private Long companyid;
}
