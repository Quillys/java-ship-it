package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {

    private static final int BASE_COST = 3;

    // Сколько дней посылка не испортится.
    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    // Испортилась, если с момента отправки прошло больше дней, чем timeToLive.
    public boolean isExpired(int currentDay) {
        int daysSinceSend = currentDay - getSendDay();
        return daysSinceSend > timeToLive;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    @Override
    protected int getBaseCost() {
        return BASE_COST;
    }
}
