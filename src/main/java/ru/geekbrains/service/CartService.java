package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.model.entity.Cart;
import ru.geekbrains.model.entity.Product;

@Service
public interface CartService {
    Cart createNewCart();
    Cart selectCartById(Integer id);
    Cart getCurrentCart();
    Cart deleteCartById(int id);
    Cart deleteCurrentCart();
    void addProductToCurrentCart(Product toAdd);
}