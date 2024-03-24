package com.example.ecommerce.Service;

import com.example.ecommerce.Exception.InvalidRatingException;
import com.example.ecommerce.Exception.ProductNotFoundException;
import com.example.ecommerce.Model.Category;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        categoryService.getCategoryById(product.getCategory().getId());
        productRepository.save(product);
    }

    public void updateProduct(Product updatedProduct, Integer id) {
        Product product = getProductById(id);
        categoryService.getCategoryById(updatedProduct.getCategory().getId());

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        product.setUnitsSold(updatedProduct.getUnitsSold());
        product.setAverageRating(updatedProduct.getAverageRating());
        product.setRatingCount(updatedProduct.getRatingCount());

        productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    // Get top-selling products for a specified category
    public List<Product> getTopSellingProducts(String categoryName) {
        List<Product> products = getAllProducts();
        List<Product> topSellingProducts = new ArrayList<>();

        for (Product product : products) {
            Category category = categoryService.getCategoryById(product.getCategory().getId());
            if (category.getName().equalsIgnoreCase(categoryName))
                topSellingProducts.add(product);
        }

        if(topSellingProducts.isEmpty())
            throw new ProductNotFoundException("No products found for category " + categoryName);

        topSellingProducts.sort(Comparator.comparingInt(Product::getUnitsSold).reversed());

        if(topSellingProducts.size() > 20)
            return topSellingProducts.subList(0, 20);

        return topSellingProducts;
    }

    // Rate a product
    public void rateProduct(Integer productId, double rating) {
        if(rating < 0 || rating > 5)
            throw new InvalidRatingException("Rating must be a number between 0 and 5");

        Product product = getProductById(productId);
        double averageRating = product.getAverageRating();
        int ratingCount = product.getRatingCount();

        double newAverageRating = (averageRating * ratingCount + rating) / (ratingCount + 1);
        product.setAverageRating(newAverageRating);
        product.setRatingCount(ratingCount + 1);

        productRepository.save(product);
    }
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }
}
