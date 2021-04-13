package ru.geekbrains.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new Product(1, "One", 100));
        products.add(new Product(2, "Two", 103));
        products.add(new Product(3, "Three", 102));
        products.add(new Product(4, "Four", 120));
        products.add(new Product(5, "Five", 130));
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProductById(final int id) {
        return products.stream().filter(prod -> prod.getId() == id).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        products.forEach(prod -> result.append(prod.getId()).append(". ").append(prod.getTitle()).
                append(" стоимостью ").append(prod.getCost()).append("\n"));
        int lastEnter = result.lastIndexOf("\n");
        return result.delete(lastEnter, lastEnter + 1).toString();
    }
}
