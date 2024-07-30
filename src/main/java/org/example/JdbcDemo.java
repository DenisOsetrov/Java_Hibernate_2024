package org.example;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class JdbcDemo {

    // URL підключення до бази даних PostgreSQL
    private static String CONNECTION_URL = "jdbc:postgresql://localhost:5432/XXX";

    // Ім'я користувача бази даних
    private static String DB_USERNAME = "postgres";

    // Пароль користувача бази даних
    private static String DB_PASSWORD = "postgres";

    // Основний метод програми
    @SneakyThrows // Використовується для приховання перевірених винятків
    public static void main(String[] args) {
        // Завантаження драйвера PostgreSQL
        Class.forName("org.postgresql.Driver");

        // Виконання запиту на створення таблиці, якщо вона не існує
        executeWithStatement("create table if not exists products (id bigint, name varchar(255), price numeric)");

        // Додавання продукту з використанням підготовленого виразу
        // Це демонструє SQL ін'єкцію
        addProductWithPreparedStatement(3L, "new product', 999); delete from products where 1=1; insert into products (id, name, price) values (123, 'new product 2", 999D);
    }

    // Метод для додавання продукту за допомогою простого SQL запиту
    public static void addProduct(Long id, String name, Double price) {
        // Використання String.format для створення SQL запиту
        executeWithStatement("insert into products (id, name, price) values (%s, '%s', %s)".formatted(id, name, price));
    }

    // Метод для додавання продукту за допомогою підготовленого виразу
    public static void addProductWithPreparedStatement(Long id, String name, Double price) {
        executeWithPreparedStatement("insert into products (id, name, price) values (?, ?, ?)", id, name, price);
    }

    // Метод для виконання SQL запиту за допомогою об'єкта Statement
    @SneakyThrows // Обробка винятків
    public static void executeWithStatement(String query) {
        // Використання try-with-resources для автоматичного закриття ресурсів
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD)) {
            Statement statement = connection.createStatement();
            System.out.println("Executing: " + query);
            statement.execute(query); // Виконання SQL запиту
        }
    }

    // Метод для виконання підготовленого SQL виразу
    @SneakyThrows // Обробка винятків
    public static void executeWithPreparedStatement(String query, Object... params) {
        // Використання try-with-resources для автоматичного закриття ресурсів
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Заповнення параметрів підготовленого виразу
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.execute(); // Виконання SQL запиту
        }
    }
}

                                        // Коментар до коду:

//Підключення до бази даних:
    //URL підключення: jdbc:postgresql://localhost:5432/XXX вказує на базу даних PostgreSQL,
    // яка розташована локально на порту 5432. Ім'я бази даних потрібно замінити на актуальне (XXX).
    //Ім'я користувача та пароль: Змінні DB_USERNAME та DB_PASSWORD використовуються для аутентифікації в базі даних.

//Драйвер:
    //Class.forName("org.postgresql.Driver") завантажує драйвер PostgreSQL, що дозволяє Java взаємодіяти з базою даних.

//SQL запити:
    //Виконання SQL запитів: Метод executeWithStatement виконує SQL запити з використанням Statement, що небезпечно щодо SQL ін'єкцій.
    //Підготовлені вирази: Метод executeWithPreparedStatement використовує PreparedStatement,
    // що захищає від SQL ін'єкцій, бо параметри передаються окремо від запиту.

//SQL ін'єкція:
    //Приклад у addProductWithPreparedStatement: У цьому рядку коду демонструється SQL ін'єкція,
    // яка видалить всі дані з таблиці products. Це робиться для ілюстрації,
    // чому потрібно використовувати підготовлені вирази.