package com.bootcampproject.entities;


import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Role")
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String authority;
    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    private List<User> user;
}