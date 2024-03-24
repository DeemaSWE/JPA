package com.example.ecommerce.Repository;

import com.example.ecommerce.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantStockRepository extends JpaRepository<MerchantStock, Integer> {
    MerchantStock findByProductIdAndMerchantId(Integer productId, Integer merchantId);

}