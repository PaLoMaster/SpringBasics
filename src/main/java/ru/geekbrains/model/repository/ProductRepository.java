package ru.geekbrains.model.repository;

import org.springframework.stereotype.Component;
import ru.geekbrains.model.entity.Product;

import java.util.Map;

@Component
public interface ProductRepository {
    Map<Integer, Product> getProducts();
    Product getProductById(int id);
}