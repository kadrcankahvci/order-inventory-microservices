package com.ecommerce.inventoryservice.service;
import com.ecommerce.inventoryservice.dto.InventoryRequest;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        System.out.println("Aranan SKU: " + skuCode);

        var inventory = inventoryRepository.findBySkuCode(skuCode);

        if(inventory.isPresent()) {
            System.out.println("BULDUM! Stok miktarı: " + inventory.get().getQuantity());
            return inventory.get().getQuantity() > 0;
        } else {
            System.out.println("HATA: Veritabanında " + skuCode + " bulunamadı!");
            return false;
        }

    }
    @Transactional
    public void createNewInventory(InventoryRequest inventoryRequest) {
        // 1. Zaten bu SKU koduna sahip bir kayıt var mı kontrol et (Önemli!)
        if (inventoryRepository.findBySkuCode(inventoryRequest.getSkuCode()).isPresent()) {
            log.info("Inventory record for SKU {} already exists. Skipping...", inventoryRequest.getSkuCode());
            return;
        }

        // 2. DTO'dan gelen verilerle yeni modelimizi (Entity) kuruyoruz
        Inventory inventory = new Inventory();
        inventory.setSkuCode(inventoryRequest.getSkuCode());
        inventory.setQuantity(inventoryRequest.getQuantity());

        // 3. Veritabanına kaydet
        inventoryRepository.save(inventory);
        log.info("New inventory record created for SKU: {} with quantity: {}",
                inventoryRequest.getSkuCode(), inventoryRequest.getQuantity());
    }
    @Transactional
    public void reduceStock(String skuCode, Integer quantity) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new RuntimeException("Product not found in inventory:"+ skuCode));
        if(inventory.getQuantity()< quantity){
            throw  new RuntimeException("Not enough stock for SKU:"+ skuCode);
        } else {
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
            log.info("Stock reduced for SKU: {}. New quantity: {}", skuCode, inventory.getQuantity());
        }
    }
}
