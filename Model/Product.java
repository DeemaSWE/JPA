package com.example.ecommerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, message = "Name must be at least 3 characters")
    @Column(columnDefinition = "varchar(50) not null check (length(name) >= 3)")
    private String name;

    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be a positive number")
    @Column(columnDefinition = "double not null")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull(message = "Units sold cannot be empty")
    @PositiveOrZero(message = "Units sold must be a positive number")
    @Column(columnDefinition = "int not null")
    private Integer unitsSold;

    @Min(value = 0, message = "Average rating must be a number between 0 and 5")
    @Max(value = 5, message = "Average rating must be a number between 0 and 5")
    @Column(columnDefinition = "double not null")
    private Double averageRating;

    @PositiveOrZero(message = "Rating count must be a positive number")
    @Column(columnDefinition = "int not null")
    private Integer ratingCount;
}
