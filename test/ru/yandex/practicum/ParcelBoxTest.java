package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.StandardParcel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParcelBoxTest {

    private static final int MAX_WEIGHT = 10;

    private ParcelBox<StandardParcel> box;

    @BeforeEach
    void setUp() {
        box = new ParcelBox<>(MAX_WEIGHT);
    }

    @Test
    void addsParcelWhenMaxWeightNotExceeded() {
        StandardParcel parcel = createParcel("Учебники", 4);

        assertTrue(box.addParcel(parcel));
        assertEquals(List.of(parcel), box.getAllParcels());
    }

    @Test
    void doesNotAddParcelWhenMaxWeightExceeded() {
        StandardParcel books = createParcel("Учебники", 6);
        box.addParcel(books);

        assertFalse(box.addParcel(createParcel("Спортивные грузы", 5))); // 6 + 5 = 11 > 10
        assertEquals(List.of(books), box.getAllParcels());
    }

    @Test
    void addsParcelWhenTotalWeightEqualsMaxWeight() {
        box.addParcel(createParcel("Учебники", 6));

        assertTrue(box.addParcel(createParcel("Игрушки", 4))); // 6 + 4 = 10 — ровно максимум
        assertEquals(2, box.getAllParcels().size());
    }

    @Test
    void rejectedParcelDoesNotTakeUpSpace() {
        box.addParcel(createParcel("Учебники", 6));
        box.addParcel(createParcel("Гантели", 5)); // не поместилась

        assertTrue(box.addParcel(createParcel("Игрушки", 4)));
    }

    private static StandardParcel createParcel(String description, int weight) {
        return new StandardParcel(description, weight, "Москва, ул. Пушкина, дом Колотушника", 1);
    }
}
