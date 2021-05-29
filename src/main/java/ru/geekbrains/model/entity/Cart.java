package ru.geekbrains.model.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Cart {
    private int id;
    private List<Product> products = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product toAdd) {
        products.add(toAdd);
    }

    public void addProducts(List<Product> toAdd) {
        products.addAll(toAdd);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Корзина № ").append(id).append(" с продуктами");
        if (products.isEmpty()) {
            result.append(" пуста.");
        } else {
            result.append(":\n\t");
            products.forEach(prod -> result.append(prod.toString()).append("\n\t"));
            int lastEnterAndTab = result.lastIndexOf("\n\t");
            result.delete(lastEnterAndTab, lastEnterAndTab + 2);
        }
        return result.toString();
    }
}
