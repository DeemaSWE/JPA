package com.example.ecommerce.Repository;

import com.example.ecommerce.Model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
}