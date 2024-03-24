package com.example.ecommerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MerchantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @NotNull(message = "Stock cannot be empty")
    @Min(value = 10, message = "Stock must be at least 10")
    @Column(columnDefinition = "int not null")
    private Integer stock;
}
