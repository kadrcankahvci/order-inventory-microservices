package com.ecommerce.inventoryservice;

import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.ecommerce.inventoryservice.model.Inventory;


@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(InventoryServiceApplication.class, args);

    }
    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){
        return args -> {
            // Tabloda veri var mı diye bakıyoruz
            if (inventoryRepository.count() == 0) {
                System.out.println("Veritabanı boş, örnek veriler ekleniyor...");

                Inventory inventory = new Inventory();
                inventory.setSkuCode("iphone_15");
                inventory.setQuantity(100);

                Inventory inventory1 = new Inventory();
                inventory1.setSkuCode("pixel_8");
                inventory1.setQuantity(0);

                inventoryRepository.save(inventory);
                inventoryRepository.save(inventory1);

                System.out.println("Örnek veriler başarıyla eklendi! Artık test edebilirsin.");
            } else {
                System.out.println("Veritabanında zaten veri var, tekrar ekleme yapılmadı.");
            }
        };
    }

}
