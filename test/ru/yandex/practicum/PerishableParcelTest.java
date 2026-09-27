package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.PerishableParcel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerishableParcelTest {

    // Отправлена 10-го числа и не портится 3 дня. Последний день, когда она ещё свежая — 13-е.
    private final PerishableParcel parcel =
            new PerishableParcel("Торт от девушки", 2, "Владивосток, ул. Калинина, 12",
                    10, 3);

    @Test
    void isNotExpiredBeforeTimeToLiveEnds() {
        assertFalse(parcel.isExpired(12));
    }

    @Test
    void isExpiredLongAfterTimeToLiveEnds() {
        assertTrue(parcel.isExpired(20));
    }

    @Test
    void isNotExpiredOnLastDayOfTimeToLive() {
        assertFalse(parcel.isExpired(13));
    }

    @Test
    void isExpiredOnFirstDayAfterTimeToLive() {
        assertTrue(parcel.isExpired(14));
    }
}
