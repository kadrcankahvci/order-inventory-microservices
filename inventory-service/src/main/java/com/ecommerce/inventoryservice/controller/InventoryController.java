package com.ecommerce.inventoryservice.controller;
import com.ecommerce.inventoryservice.dto.InventoryRequest;
import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor

public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    public boolean isInStock(@PathVariable("sku-code") String skucode){
        return inventoryService.isInStock(skucode);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createInventory(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.createNewInventory(inventoryRequest);
    }
    @PutMapping("/reduce")
    @ResponseStatus(HttpStatus.OK)
    public  void reduceStock(@RequestParam String skuCode, @RequestParam Integer quantity){
        inventoryService.reduceStock(skuCode,quantity);
    }
}



