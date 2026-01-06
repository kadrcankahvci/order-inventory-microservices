package com.ecommerce.orderservice.service;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor


public class OrderService {
    private final OrderRepository orderRepository;
    private  final WebClient webClient;
    public void placeOrder(Order orderRequest){

        Boolean isInStock = webClient.get()
                .uri("http://localhost:8082/api/inventory/" + orderRequest.getSkuCode())
                .retrieve()
                .bodyToMono(Boolean.class) // Gelen cevabı Boolean olarak oku
                .block(); // Cevap gelene kadar bekle (Senkron yapı)

        if(Boolean.TRUE.equals(isInStock)){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setSkuCode(orderRequest.getSkuCode());
            order.setPrice(orderRequest.getPrice());
            order.setQuantity(orderRequest.getQuantity());

            orderRepository.save(order);
            System.out.println("Sipariş başarıyla oluşturuldu!");
        } else {
            throw new IllegalArgumentException("Üzgünüz, ürün stokta kalmamış!");
        }


    }

}
