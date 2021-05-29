package ru.geekbrains.model.repository;

import org.springframework.stereotype.Component;
import ru.geekbrains.model.entity.Product;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    private Map<Integer, Product> products = new HashMap<>();

    public ProductRepositoryImpl() {
        products.put(1, new Product(1, "One", 100));
        products.put(2, new Product(2, "Two", 103));
        products.put(3, new Product(3, "Three", 102));
        products.put(4, new Product(4, "Four", 120));
        products.put(5, new Product(5, "Five", 130));
    }

    @Override
    public Map<Integer, Product> getProducts() {
        return products;
    }

    @Override
    public Product getProductById(int id) {
        return products.getOrDefault(id, null);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        products.values().forEach(prod -> result.append(prod.toString()).append("\n"));
        int lastEnter = result.lastIndexOf("\n");
        return result.delete(lastEnter, lastEnter + 1).toString();
    }
}