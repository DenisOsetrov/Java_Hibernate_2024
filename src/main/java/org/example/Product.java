package org.example;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity // вказує, що цей Клас є сутністю (entity) і його екземпляри будуть зберігатися в базі даних.
@Table(name = "products")  // Визначає назву таблиці в базі даних, до якої прив'язана ця сутність
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Вказує, що значення ідентифікатора буде згенеровано автоматично
    private Long id;

    private String name;  // Поле для зберігання імені продукту

    private Double price; // Поле для зберігання ціни продукту

    @Column(name = "current_discount") // Вказує назву стовпця у базі даних, який зберігає поточну знижку
    private Double currentDiscount;
}

