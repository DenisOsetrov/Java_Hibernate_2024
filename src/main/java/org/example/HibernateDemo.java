package org.example;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateDemo {

    public static void main(String[] args) {
        // Ініціалізація реєстру сервісів Hibernate
        try (StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure() // Читання конфігурації з hibernate.cfg.xml
                .build()) {

            // Створення метаданих з класом, який має анотацію @Entity
            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(Product.class) // Додавання класу Product
                    .getMetadataBuilder()
                    .build();

            // Створення SessionFactory для роботи з сесіями
            try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
                 Session session = sessionFactory.openSession()) {

                // Початок транзакції
                Transaction transaction = session.beginTransaction();

                // Створення нового продукту
                Product product = new Product();
                product.setName("Laptop1");
                product.setPrice(3999.99);

                // Збереження продукту в базу даних
                session.persist(product); // INSERT

                // Оновлення ціни продукту
                product.setPrice(3499.99);
                session.merge(product); // UPDATE

                // Відключення об'єкта продукту від сесії
                session.detach(product);

                // Зміна ціни продукту
                product.setPrice(2399D);

                // Видалення продукту з бази даних
                session.remove(product); // DELETE

                // Повторне збереження продукту в базу даних
                session.persist(product); // INSERT

                // Коміт транзакції
                transaction.commit();
            } catch (HibernateException e) {
                // Обробка винятків Hibernate
                throw new RuntimeException(e);
            }
        }
    }
}

//                                       Коментарі до коду:

//Ініціалізація Hibernate:
    //Реєстр сервісів: StandardServiceRegistry конфігурується для налаштування Hibernate з файлу hibernate.cfg.xml.
    //Метадані: MetadataSources використовуються для налаштування класів з анотаціями.

//Сесії та транзакції:
    //SessionFactory: Створюється для створення сесій, які використовуються для виконання операцій з базою даних.
    //Сесія: Session забезпечує взаємодію з базою даних (відкриття, робота з об'єктами, закриття).
    //Транзакції: Починаються з session.beginTransaction() і завершуються transaction.commit(). Це дозволяє виконувати кілька операцій як єдине ціле.

//Операції з об'єктами:
    //session.persist(product): Зберігає новий об'єкт у базі даних (виконує SQL INSERT).
    //session.merge(product): Оновлює існуючий об'єкт у базі даних (виконує SQL UPDATE).
    //session.detach(product): Відключає об'єкт від сесії, щоб зміни в об'єкті не відслідковувалися Hibernate.
    //session.remove(product): Видаляє об'єкт з бази даних (виконує SQL DELETE).

//Обробка винятків:
    //Використовується try-catch для обробки винятків Hibernate (HibernateException),
    // що дозволяє обробляти помилки, які можуть виникнути при роботі з базою даних.