package com.ecommerce.inventoryservice.service;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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
}
