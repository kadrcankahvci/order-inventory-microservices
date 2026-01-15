package com.ecommerce.orderservice.service;
import com.ecommerce.orderservice.client.InventoryClient;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional

public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(Order orderRequest){

        boolean isInStock = inventoryClient.isInStock(orderRequest.getSkuCode());

        if(Boolean.TRUE.equals(isInStock)){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setSkuCode(orderRequest.getSkuCode());
            order.setPrice(orderRequest.getPrice());
            order.setQuantity(orderRequest.getQuantity());

            orderRepository.save(order);
            inventoryClient.reduceStock(orderRequest.getSkuCode(), orderRequest.getQuantity());
            System.out.println("Sipariş başarıyla oluşturuldu!");
        } else {
            throw new IllegalArgumentException("Üzgünüz, ürün stokta kalmamış!");
        }


    }

}
