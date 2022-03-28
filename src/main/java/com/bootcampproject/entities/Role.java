package com.bootcampproject.entities;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Role")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String authority;
    @ManyToMany(mappedBy = "role")
    private List<User> user;

    Role(String authority)
    {
        this.authority=authority;
    }
}