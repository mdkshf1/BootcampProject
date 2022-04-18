package com.bootcampproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Where(clause = "is_deleted=false")
public class Product extends AuditingInfo implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Seller seller;

    @NotBlank(message = "Name cannot be Blank")
    @NotNull(message = "Name cannot be Null")
    @Size(min = 3,message = "Name should be correct with at least 3 characters")
    private String name;

    private String description;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Category category;

    private Boolean isCancellable = false;
    private Boolean isReturnable = false;
    @NotNull(message = "Brand cannot be null")
    @NotBlank(message = "Brand cannot be blank")
    private String brand;
    private Boolean isActive = false;
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "product")
    private List<ProductVariation> productVariation;

    @OneToMany(mappedBy = "products")
    private List<ProductReview> productReviews;
}
