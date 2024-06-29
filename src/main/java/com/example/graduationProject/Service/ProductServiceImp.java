package com.example.graduationProject.Service;

import com.example.graduationProject.Entity.Product;
import com.example.graduationProject.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService{
    @Autowired
    ProductRepository productRepository;
    @Override
    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> fetchAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product updateProduct(int productId, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            Product existingProduct = optionalProduct.get();
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductDesc(product.getProductDesc());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setProductId(product.getProductId());
            existingProduct.setPrice(product.getPrice());
            return productRepository.save(existingProduct);
        }
        else {
            return null;
        }
    }


    @Override
    public boolean deleteProduct(int productId)  {
        if (productRepository.findById(productId).isPresent()){
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    @Override
    public Product addProductImages(int productId, MultipartFile productImage, MultipartFile additionalProductImage) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setImage(productImage.getBytes());
        if (Objects.nonNull(additionalProductImage))
            product.setAdditionalImage(additionalProductImage.getBytes());

        return productRepository.save(product);
    }
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> searchProducts(String productName, String category) {
        if (productName != null && !productName.isEmpty() && category != null && !category.isEmpty()) {
            return productRepository.findByProductNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(productName, category);
        } else if (productName != null && !productName.isEmpty()) {
            return productRepository.findByProductNameContainingIgnoreCase(productName);
        } else if (category != null && !category.isEmpty()) {
            return productRepository.findByCategoryContainingIgnoreCase(category);
        } else {
            return productRepository.findAll();
        }
    }


}
