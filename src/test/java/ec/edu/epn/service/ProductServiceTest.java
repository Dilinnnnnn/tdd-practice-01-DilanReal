package ec.edu.epn.service;

import ec.edu.epn.model.Category;
import ec.edu.epn.model.Product;
import ec.edu.epn.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Tests")
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electrónica");

        product = new Product();
        product.setId(1L);
        product.setSku("SKU001");
        product.setName("Laptop");
        product.setDescription("Laptop de alta gama");
        product.setPrice(new BigDecimal("1500.00"));
        product.setStock(10);
        product.setCategory(category);
        product.setActive(true);
    }

    // findBySku
    @Test
    @DisplayName("P1: findBySku puede retornar un producto existente")
    void testFindBySkuPuederetornarUnProductoExistente() {
        when(productRepository.findBySku("SKU001")).thenReturn(Optional.of(product));
        Product result = productService.findBySku("SKU001");
        assertNotNull(result);
        assertEquals("SKU001", result.getSku());
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).findBySku("SKU001");
    }

    @Test
    @DisplayName("P1: findBySku puede retornar null si el producto no existe")
    void testFindBySkuPuedeRetornarNullSiElProductoNoExiste() {
        when(productRepository.findBySku("SKU002")).thenReturn(Optional.empty());
        Product result = productService.findBySku("SKU002");
        assertNull(result);
        verify(productRepository, times(1)).findBySku("SKU002");
    }

    // findActiveProducts
    @Test
    @DisplayName("P2: findActiveProducts puede retornar una lista de productos activos")
    void testFindActiveProductsPuedeRetornarUnaListaDeProductosActivos() {
        Product product2 = new Product();
        product2.setId(2L);
        product2.setSku("SKU002");
        product2.setName("Smartphone");
        product2.setDescription("Smartphone de última generación");
        product2.setPrice(new BigDecimal("800.00"));
        product2.setStock(20);
        product2.setCategory(category);
        product2.setActive(true);

        when(productRepository.findByActiveTrue()).thenReturn(Arrays.asList(product, product2));
        List<Product> result = productService.findActiveProducts();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(Product::isActive));
        verify(productRepository, times(1)).findByActiveTrue();
    }
}