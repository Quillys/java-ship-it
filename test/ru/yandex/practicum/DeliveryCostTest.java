package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.Parcel;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Стоимость доставки = вес × базовая стоимость: 2 — стандартная, 3 — скоропортящаяся, 4 — хрупкая.
public class DeliveryCostTest {

    private static final String ADDRESS = "Москва, ул. Пушкина, дом Колотушника";
    private static final int SEND_DAY = 5;
    private static final int TIME_TO_LIVE = 3;

    @Test
    void standardParcelCostIsWeightTimesTwo() {
        Parcel parcel = new StandardParcel("Комиксы", 10, ADDRESS, SEND_DAY);

        assertEquals(20, parcel.calculateDeliveryCost());
    }

    @Test
    void standardParcelWithMinimalWeightCostsBaseCost() {
        Parcel parcel = new StandardParcel("Открытка", 1, ADDRESS, SEND_DAY);

        assertEquals(2, parcel.calculateDeliveryCost());
    }

    @Test
    void fragileParcelCostIsWeightTimesFour() {
        Parcel parcel = new FragileParcel("Светильник", 10, ADDRESS, SEND_DAY);

        assertEquals(40, parcel.calculateDeliveryCost());
    }

    @Test
    void fragileParcelWithMinimalWeightCostsBaseCost() {
        Parcel parcel = new FragileParcel("Хрупкая игрушка", 1, ADDRESS, SEND_DAY);

        assertEquals(4, parcel.calculateDeliveryCost());
    }

    @Test
    void perishableParcelCostIsWeightTimesThree() {
        Parcel parcel = new PerishableParcel("Тортик", 10, ADDRESS, SEND_DAY, TIME_TO_LIVE);

        assertEquals(30, parcel.calculateDeliveryCost());
    }

    @Test
    void perishableParcelWithMinimalWeightCostsBaseCost() {
        Parcel parcel = new PerishableParcel("Мороженное", 1, ADDRESS, SEND_DAY, TIME_TO_LIVE);

        assertEquals(3, parcel.calculateDeliveryCost());
    }
}
