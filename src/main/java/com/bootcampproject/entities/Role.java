package com.bootcampproject.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
public class Role {
    @Id
    private Integer id;
    private String authority;

    @ManyToMany
    private List<User> user;
}