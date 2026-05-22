package ec.edu.epn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.epn.model.Product;
import ec.edu.epn.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku).orElse(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product update(Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Producto no existe");
        }
        return productRepository.save(product);
    }

    public List<Product> findActiveProducts() {
        return productRepository.findByActiveTrue();
    }

}