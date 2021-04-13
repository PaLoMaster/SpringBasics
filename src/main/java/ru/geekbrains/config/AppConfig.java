package ru.geekbrains.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.model.Cart;
import ru.geekbrains.model.ProductRepository;

@Configuration
@ComponentScan("ru.geekbrains.model")
public class AppConfig {
    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository();
    }

    @Bean
    public Cart cart() {
        return new Cart();
    }
}
