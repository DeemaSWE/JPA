package com.example.ecommerce.Service;

import com.example.ecommerce.Exception.InsufficientBalanceException;
import com.example.ecommerce.Exception.ProductOutOfStockException;
import com.example.ecommerce.Exception.UserNotFoundException;
import com.example.ecommerce.Model.MerchantStock;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User updatedUser, Integer id) {
        User user = getUserById(id);

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());
        user.setBalance(updatedUser.getBalance());

        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    // Buy a product from a merchant
    public void buyProduct(Integer userId, Integer productId, Integer merchantId) {
        User user = getUserById(userId);
        Product product = productService.getProductById(productId);
        MerchantStock merchantStock = merchantStockService.getMerchantStockByProductIdAndMerchantId(productId, merchantId);

        if (merchantStock.getStock() == 0)
            throw new ProductOutOfStockException("Product is out of stock");

        if (user.getBalance() < product.getPrice())
            throw new InsufficientBalanceException("Insufficient balance");

        user.setBalance(user.getBalance() - product.getPrice());
        merchantStock.setStock(merchantStock.getStock() - 1);
        product.setUnitsSold(product.getUnitsSold() + 1);

        userRepository.save(user);
        merchantStockService.updateMerchantStock(merchantStock, merchantId);
        productService.updateProduct(product, productId);
    }

    // Send a gift card to another user
    public void sendGiftCard(Integer senderId, Integer receiverId, double amount) {
        User sender = getUserById(senderId);
        User receiver = getUserById(receiverId);

        if (sender.getBalance() < amount)
            throw new InsufficientBalanceException("Insufficient balance");

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);
    }
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

}
