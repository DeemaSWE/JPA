package com.example.ecommerce.Service;

import com.example.ecommerce.Exception.MerchantNotFoundException;
import com.example.ecommerce.Model.Merchant;
import com.example.ecommerce.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    public void addMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public void updateMerchant(Merchant updatedMerchant, Integer id) {
        Merchant merchant = getMerchantById(id);

        merchant.setName(updatedMerchant.getName());

        merchantRepository.save(merchant);
    }

    public void deleteMerchant(Integer id) {
        Merchant merchant = getMerchantById(id);
        merchantRepository.delete(merchant);
    }

    public Merchant getMerchantById(Integer id) {
        return merchantRepository.findById(id).orElseThrow(() -> new MerchantNotFoundException("Merchant with id " + id + " not found"));
    }

}
