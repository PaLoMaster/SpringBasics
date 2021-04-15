package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.config.AppConfig;
import ru.geekbrains.model.Cart;
import ru.geekbrains.model.Product;
import ru.geekbrains.model.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<Cart> carts = new ArrayList<>();
    private static Cart currentCart;
    private static ApplicationContext context;
    private static Scanner input;

    private static ProductRepository products;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        products = context.getBean(ProductRepository.class);
        input = new Scanner(System.in);
        System.out.println("Добро пожаловать в наш консольный магазин!");
        while (true) {
            System.out.println("createCart - создать корзину");
            System.out.println("deleteCart - удалить текущую корзину и забыть её");
            System.out.println("deleteCart N - удалить корзину № N");
            System.out.println("selectCart - выбрать сохранённую корзину из списка");
            System.out.println("selectCart N - выбрать сохранённую корзину № N");
            System.out.println("exit - выход из магазина");
            if (currentCart != null) {
                System.out.println("Список доступных товаров:\n" + products.toString());
                System.out.println("Текущая корзина:\n" + currentCart.toString());
            } else {
                System.out.println("Текущая корзина не выбрана. Для начала выберите / создайте корзину.");
            }
            String in = input.next();
            switch (in) {
                case "createCart":
                    currentCart = getNewCart();
                    break;
                case "deleteCart":
                    deleteCart();
                    break;
                case "selectCart":
                    selectCart();
                    break;
                case "exit":
                    System.out.println("Спасибо, что воспользовались нашими услугами.");
                    System.out.println("Будем рады видеть Вас снова.");
                    return;
                default:
                    if (in.startsWith("deleteCart ")) {
                        deleteCartById(in);
                    } else if (in.startsWith("selectCart ")) {
                        selectCartById(in);
                    } else {
                        addProductById(in);
                    }
            }
        }
    }

    private static Cart getNewCart() {
        Cart cart = context.getBean(Cart.class);
        int id = carts.size();
        cart.setId(id);
        carts.add(cart);
        System.out.println("Создана корзина № " + (id + 1));
        return cart;
    }

    private static void deleteCart() {
        while (true) {
            System.out.println("Список доступных корзин:\n" + cartsToString());
            System.out.println("Введите номер корзины для удаления (или -1 для выхода из режима удаления корзины)");
            int id = input.nextInt();
            if (id == -1 || deleteCartById(id) != null)
                break;
        }
    }

    private static Cart deleteCartById(int id) {
        Cart toDelete = carts.stream().filter(cart -> cart.getId() == id - 1).findFirst().orElse(null);
        if (toDelete != null) {
            carts.remove(toDelete);
            System.out.print("Удалена ");
            if (currentCart.getId() == toDelete.getId()) {
                System.out.print("текущая ");
                currentCart = null;
            }
            System.out.println(toDelete.toString());
            return toDelete;
        } else {
            System.out.println("Корзина № " + id + " не найдена");
            return null;
        }
    }

    private static void deleteCartById(String stringId) {
        int id;
        if (stringId.startsWith("deleteCart ")) {
            id = Integer.parseInt(stringId.replace("deleteCart ", ""));
        } else {
            id = Integer.parseInt(stringId);
        }
        if (deleteCartById(id) == null) {
            deleteCart();
        }
    }

    private static void selectCart() {
        while (true) {
            System.out.println("Список доступных корзин:\n" + cartsToString());
            System.out.println("Введите номер корзины для выбора (или -1 для выхода из режима выбора корзины)");
            int id = input.nextInt();
            if (id == -1 || selectCartById(id) != null)
                break;
        }
    }

    private static Cart selectCartById(int id) {
        Cart toSelect = carts.stream().filter(cart -> cart.getId() == id - 1).findFirst().orElse(null);
        if (toSelect != null) {
            currentCart = toSelect;
            System.out.println("Выбрана " + toSelect.toString());
            return toSelect;
        } else {
            System.out.println("Корзина № " + id + " не найдена.");
            return null;
        }
    }

    private static void selectCartById(String stringId) {
        int id;
        if (stringId.startsWith("selectCart ")) {
            id = Integer.parseInt(stringId.replace("selectCart ", ""));
        } else {
            id = Integer.parseInt(stringId);
        }
        if (selectCartById(id) == null) {
            selectCart();
        }
    }

    private static void addProductById(String stringId) {
        if (currentCart != null) {
            int prodId = Integer.parseInt(stringId);
            Product toAdd = products.getProductById(prodId);
            if (toAdd != null) {
                currentCart.addProduct(toAdd);
                System.out.println("Продукт добавлен в корзину:" + toAdd.toString());
            } else {
                System.out.println("Ошибка ввода!");
            }
        } else {
            System.out.println("Не выбрана текущая корзина. Сперва выберите корзину.");
        }
    }

    private static String cartsToString() {
        StringBuilder result = new StringBuilder();
        if (carts.isEmpty()) {
            result.append("Список корзин пуст.");
        } else {
            carts.forEach(cart -> result.append(cart.toString()).append("\n"));
            int lastEnter = result.lastIndexOf("\n");
            result.delete(lastEnter, lastEnter + 1);
        }
        return result.toString();
    }
}
