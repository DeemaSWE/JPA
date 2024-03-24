package com.example.ecommerce.Repository;

import com.example.ecommerce.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}