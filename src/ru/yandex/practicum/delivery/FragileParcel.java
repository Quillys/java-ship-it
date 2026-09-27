package ru.yandex.practicum.delivery;

public class FragileParcel extends Parcel implements Trackable {

    private static final int BASE_COST = 4;

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    // Сначала оборачиваем в плёнку, а сообщение об упаковке берём из родительского класса.
    @Override
    public void packageItem() {
        System.out.println("Посылка <<" + getDescription() + ">> обёрнута в защитную плёнку");
        super.packageItem();
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.println("Хрупкая посылка <<" + getDescription() + ">> изменила местоположение на " + newLocation);
    }

    @Override
    protected int getBaseCost() {
        return BASE_COST;
    }
}
