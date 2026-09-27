package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

/*
 Коробка с посылками одного типа. Тип фиксируется при создании:
 в ParcelBox<FragileParcel> можно положить только хрупкие посылки.
*/
public class ParcelBox<T extends Parcel> {

    private final int maxWeight;
    private final List<T> parcels = new ArrayList<>();
    private int currentWeight;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    // Кладёт посылку в коробку, если не будет превышен максимальный вес.
    public boolean addParcel(T parcel) {
        int remainingWeight = maxWeight - currentWeight;
        if (parcel.getWeight() > remainingWeight) {
            System.out.println("Нельзя добавить посылку <<" + parcel.getDescription()
                    + ">>: будет превышен максимальный вес коробки (" + maxWeight + ")");
            return false;
        }
        parcels.add(parcel);
        currentWeight += parcel.getWeight();
        return true;
    }

    // Возвращаем копию, чтобы содержимое коробки нельзя было изменить в обход addParcel.
    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }
}
