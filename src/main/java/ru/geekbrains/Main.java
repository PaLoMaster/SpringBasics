package ru.geekbrains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.geekbrains.config.AppConfig;
import ru.geekbrains.model.entity.Product;
import ru.geekbrains.model.repository.ProductRepository;
import ru.geekbrains.service.CartService;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Main {

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private final ProductRepository products;

    private final CartService cartService;

    private final String CREATE_CART_PREFIX = "createCart";
    private final String DELETE_CART_PREFIX = "deleteCart";
    private final String DELETE_CART_BY_NUM_PREFIX = "deleteCart ";
    private final String SELECT_CART_BY_NUM_PREFIX = "selectCart ";
    private final String CART_NUM_POSTFIX = "N";
    private final String EXIT_COMMAND = "exit";

    public Main(ProductRepository products, CartService cartService) {
        this.products = products;
        this.cartService = cartService;
    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @PostConstruct
    public void start() {
        System.out.println("Добро пожаловать в наш консольный магазин!");
        while (true) {
            System.out.println(CREATE_CART_PREFIX + " - создать корзину");
            System.out.println(DELETE_CART_PREFIX + " - удалить текущую корзину и забыть её");
            System.out.println(DELETE_CART_BY_NUM_PREFIX + CART_NUM_POSTFIX + " - удалить корзину № " + CART_NUM_POSTFIX);
            System.out.println(SELECT_CART_BY_NUM_PREFIX + CART_NUM_POSTFIX + " - выбрать сохранённую корзину № " + CART_NUM_POSTFIX);
            System.out.println(EXIT_COMMAND + " - выход из магазина");
            if (cartService.getCurrentCart() != null) {
                System.out.println("Список доступных товаров:\n" + products.toString());
                System.out.println("M - добавить в текущую корзину продукт № M");
            }
            String in = readInput();
            switch (in) {
                case CREATE_CART_PREFIX:
                    cartService.createNewCart();
                    break;
                case DELETE_CART_PREFIX:
                    cartService.deleteCurrentCart();
                    break;
                case EXIT_COMMAND:
                    System.out.println("Спасибо, что воспользовались нашими услугами.");
                    System.out.println("Будем рады видеть Вас снова.");
                    return;
                default:
                    if (in.startsWith(DELETE_CART_BY_NUM_PREFIX)) {
                        int id = getId(in, DELETE_CART_BY_NUM_PREFIX);
                        cartService.deleteCartById(id);
                    } else if (in.startsWith(SELECT_CART_BY_NUM_PREFIX)) {
                        int id = getId(in, SELECT_CART_BY_NUM_PREFIX);
                        cartService.selectCartById(id);
                    } else {
                        int id = getId(in, "");
                        Product toAdd = products.getProductById(id);
                        cartService.addProductToCurrentCart(toAdd);
                    }
            }
        }
    }

    private int getId(String from, String without) {
        String toParse = from.replace(without, "");
        try {
            return Integer.parseInt(toParse);
        } catch (final NumberFormatException e) {
            StringBuilder warning = new StringBuilder("Ошибка чтения значения из строки " + from);
            if (without.length() > 0) {
                warning.append(" (при удалении из неё префикса ").append(without).append(" остаётся ");
                if (toParse.length() > 0) {
                    warning.append(toParse);
                } else {
                    warning.append("пустая строка");
                }
                warning.append(")");
            }
            System.out.println(warning.append(".").toString());
            return 0;
        }
    }

    private String readInput() {
        try {
            return input.readLine();
        } catch (IOException e) {
            System.out.println("Ошибка чтения ввода.");
            return "";
        }
    }
}