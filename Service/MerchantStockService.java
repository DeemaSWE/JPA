package com.example.ecommerce.Service;

import com.example.ecommerce.Exception.MerchantStockNotFoundException;
import com.example.ecommerce.Model.Merchant;
import com.example.ecommerce.Model.MerchantStock;
import com.example.ecommerce.Repository.MerchantStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantService merchantService;
    private final ProductService productService;
    private final MerchantStockRepository merchantStockRepository;

    public List<MerchantStock> getAllMerchantStocks() {
        return merchantStockRepository.findAll();
    }

    public void addMerchantStock(MerchantStock merchantStock) {
        merchantService.getMerchantById(merchantStock.getMerchant().getId());
        productService.getProductById(merchantStock.getProduct().getId());
        merchantStockRepository.save(merchantStock);
    }

    public void updateMerchantStock(MerchantStock updatedMerchantStock, Integer id) {
        MerchantStock merchantStock = getMerchantStockById(id);
        merchantService.getMerchantById(updatedMerchantStock.getMerchant().getId());
        productService.getProductById(updatedMerchantStock.getProduct().getId());

        merchantStock.setProduct(updatedMerchantStock.getProduct());
        merchantStock.setMerchant(updatedMerchantStock.getMerchant());
        merchantStock.setStock(updatedMerchantStock.getStock());

        merchantStockRepository.save(merchantStock);
    }
    public void deleteMerchantStock(Integer id) {
        MerchantStock merchantStock = getMerchantStockById(id);
        merchantStockRepository.delete(merchantStock);
    }

    // Add more stocks of a product to a merchant Stock
    public void addMoreStocks(Integer productId, Integer merchantId, Integer amount) {
        MerchantStock merchantStock = getMerchantStockByProductIdAndMerchantId(productId, merchantId);
        merchantStock.setStock(merchantStock.getStock() + amount);
        merchantStockRepository.save(merchantStock);
    }

    public MerchantStock getMerchantStockByProductIdAndMerchantId(Integer productId, Integer merchantId) {
        MerchantStock merchantStock = merchantStockRepository.findByProductIdAndMerchantId(productId, merchantId);
        if (merchantStock == null)
            throw new MerchantStockNotFoundException("Merchant stock for product " + productId + " and merchant " + merchantId + " not found");

        return merchantStock;
    }

    public MerchantStock getMerchantStockById(Integer id) {
        return merchantStockRepository.findById(id).orElseThrow(() -> new MerchantStockNotFoundException("Merchant stock with id " + id + " not found"));
    }
}
