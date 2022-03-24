package com.bootcampproject.entities;

import lombok.*;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class AuditingInfo {

    private Date dateCreated;
    private Date lastUpdated;
    private String createdBy;
    private String updatedBy;
}