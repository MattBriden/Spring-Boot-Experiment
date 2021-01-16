package com.briden.boot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "entrys")
public class Entry {

    @Id
    private String id;

    @Column(name = "companyid")
    private Long companyId;
}
