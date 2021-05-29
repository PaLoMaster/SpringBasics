package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.entity.Cart;
import ru.geekbrains.model.entity.Product;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    private static final Map<Integer, Cart> carts = new HashMap<>();
    private static Cart currentCart;

    @Lookup
    public Cart getNewCart() {
        return null;
    }

    @Override
    public Cart createNewCart() {
        currentCart = getNewCart();
        int id = carts.size() + 1;
        currentCart.setId(id);
        carts.put(id, currentCart);
        System.out.println("Создана корзина: " + currentCart.toString());
        return currentCart;
    }

    @Override
    public Cart selectCartById(Integer id) {
        if (carts.containsKey(id)) {
            currentCart = carts.get(id);
            System.out.println("Выбрана корзина: " + currentCart.toString());
        } else {
            currentCart = null;
            System.out.println("Корзина № " + id + " не найдена. " + toString());
        }
        return currentCart;
    }

    @Override
    public Cart getCurrentCart() {
        if (currentCart != null) {
            System.out.println("Текущая корзина: " + currentCart.toString());
        } else {
            System.out.println("Текущая корзина не выбрана. " + toString());
        }
        return currentCart;
    }

    @Override
    public Cart deleteCartById(int id) {
        if (carts.containsKey(id)) {
            if (currentCart != null && currentCart.getId() == id) {
                return deleteCurrentCart();
            }
            Cart deleted = carts.remove(id);
            System.out.println("Удалена корзина: " + deleted.toString());
            return deleted;
        } else {
            System.out.println("Корзина № " + id + " не найдена. " + toString());
            return null;
        }
    }

    @Override
    public Cart deleteCurrentCart() {
        if (currentCart != null) {
            System.out.println("Удалена текущая корзина: " + currentCart.toString());
            Cart deleted = carts.remove(currentCart.getId());
            currentCart = null;
            return deleted;
        } else {
            System.out.println("Текущая корзина не выбрана. " + toString());
            return null;
        }
    }

    @Override
    public void addProductToCurrentCart(Product toAdd) {
        if (currentCart != null && toAdd != null) {
            currentCart.addProduct(toAdd);
            System.out.println("Продукт добавлен в текущую корзину № " + currentCart.getId() + ": " + toAdd.toString());
        } else {
            System.out.println("Не выбрана текущая корзина, либо продукт для добавления.");
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (carts.isEmpty()) {
            result.append("Список корзин пуст. Для начала выберите / создайте корзину.");
        } else {
            result.append("Список существующих корзин:\n");
            carts.values().forEach(cart -> result.append("\t").append(cart.toString()).append("\n"));
            int lastEnter = result.lastIndexOf("\n");
            result.delete(lastEnter, lastEnter + 1);
        }
        return result.toString();
    }
}