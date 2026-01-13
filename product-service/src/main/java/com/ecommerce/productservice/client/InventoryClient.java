package com.ecommerce.productservice.client;


import com.ecommerce.productservice.dto.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @PostMapping("/api/inventory/create")
    void createInventory(@RequestBody InventoryRequest inventoryRequest);
}
