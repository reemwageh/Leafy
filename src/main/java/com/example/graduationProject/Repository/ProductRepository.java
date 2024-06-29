package com.example.graduationProject.Repository;

import com.example.graduationProject.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByCategory(String category);

}
