package com.bootcampproject.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class AuditingInfo {

    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date lastUpdated;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;
}