package com.ecommerce.productservice.service;



import com.ecommerce.productservice.client.InventoryClient;

import com.ecommerce.productservice.dto.InventoryRequest;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.dto.ProductResponse;

import java.util.List;


@RequiredArgsConstructor

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;

    public  void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .skuCode(productRequest.getSkuCode())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);

        InventoryRequest inventoryRequest = InventoryRequest.builder()
                .skuCode(product.getSkuCode())
                .quantity(productRequest.getQuantity()).build();
        try {
            inventoryClient.createInventory(inventoryRequest);
            log.info("Inventory record created with quantity: {}", productRequest.getQuantity());
        } catch (Exception e) {
            log.error("Inventory creation failed for SKU: {}. Error: {}",
                    product.getSkuCode(), e.getMessage());
        }
        log.info("Product {} is saved", product.getId());
    }
    private ProductResponse mapToProductResponse(Product product) {
        // Burada ham Product'tan bilgileri alıp, temiz olan Response'a kopyalıyoruz
        return ProductResponse.builder()
                .id(product.getId())
                .skuCode(product.getSkuCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())

                .build();
    }
    public List<ProductResponse> getAllProducts() {
        // 1. Veritabanından tüm Product listesini çekiyoruz (Ham veri)
        List<Product> products = productRepository.findAll();

        // 2. Stream API ile bir "konveyör bandı" kuruyoruz
        return products.stream()
                // 3. Her bir Product'ı al, mapToProductResponse metoduna sok ve "Response"a çevir
                .map(this::mapToProductResponse)
                // 4. Çevrilenleri yeni bir liste yap
                .toList();
    }


}
