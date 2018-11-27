package ru.gb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        SQLService.connect(); //подключение к базе данных
        SQLService.createTab(); //создание таблицы
        SQLService.clearTab(); //очистка данных таблицы
        for (int i = 0; i < 10000; i++) SQLService.addProduct(i+1,"Продукт" + (i+1), "Продукт" + (i+1), (int)(Math.random() *1000 +1)); //добавление 10000 товаров
        //реализация команд по работе с данными таблицы
        while(true) {
            System.out.println("Введите команду:\n1. Для получения цены товара - /цена имя_товара;\n ");
            System.out.println("2. Для изменения цены товара - /сменитьцену имя_товара новая_цена;\n ");
            System.out.println("3. Для вывода товаров в заданном ценовом диапазоне - /товарыпоцене минимальная_цена максимальная_цена;\n ");
            System.out.println("4. Для выхода - /выход;\n ");
            String str = scanner.nextLine();
            if (str.matches("^/цена\\s.+$")) {
                Cost(str);
            } else if (str.matches("^/сменитьцену\\s.+\\s\\d+$")) {
                changeCost(str);
            } else if (str.matches("^/товарыпоцене\\s\\d+\\s\\d+$")) {
                productsbyCost(str);
            } else if (str.matches("^/выход$")) {
                break;
            } else System.out.println("Неправильная команда!!!\n");
        }
        SQLService.disconnect(); //отключение от базы данных
    }
    private static void Cost(String str) { //Вывести цену продукта
        String[] Arr = str.split( " ", 2);
        Product product = SQLService.getProduct(Arr[1]);
        if (product != null) System.out.println("Цена продукта " + product.getTitle() + " равна " + product.getCost() + "!\n");
        else System.out.println("Продукт " + Arr[1] + " отсутствует!\n");
    }
    private static void changeCost(String str) { //Изменить цену продукта
        String[] Arr = str.split( " ", 3);
        Product product = SQLService.getProduct(Arr[1]);
        if (product != null) {
            product.setCost(Integer.parseInt(Arr[2]));
            SQLService.changeProduct(product);
            System.out.println("Цена продукта " + product.getTitle() + " изменена на " + product.getCost() + "!\n");
        }
        else System.out.println("Продукт " + Arr[1] + " отсутствует!\n");
    }
    private static void productsbyCost(String str) { //Вывести список продуктов в ценовом интервале
        String[] Arr = str.split( " ", 3);
        ArrayList<Product> products = SQLService.getProductsbyCost(Integer.parseInt(Arr[1]), Integer.parseInt(Arr[2]));
        if (products != null) {
            if (products.size() > 0)
            {
                for(Product o : products) {
                    System.out.println("Продукт " + o.getTitle() + " цена " + o.getCost() + "!\n");
                }
            } else System.out.println("Продукты в этом ценовом диапазоне отсутствуют!\n");
        } else System.out.println("Продукты в этом ценовом диапазоне отсутствуют!\n");
    }
}
