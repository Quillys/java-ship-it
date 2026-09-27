package ru.yandex.practicum.delivery;

// Интерфейс для отправки с трекингом.
public interface Trackable {

    void reportStatus(String newLocation);
}
