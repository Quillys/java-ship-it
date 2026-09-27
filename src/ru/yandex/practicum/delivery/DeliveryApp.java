package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    // Максимальный вес посылок в одной коробке.
    private static final int BOX_MAX_WEIGHT = 50;

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();
    // Лист для трекинга посылок.
    private static final List<Trackable> trackableParcels = new ArrayList<>();

    // Создаем коробки на каждый тип посылок.
    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(BOX_MAX_WEIGHT);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(BOX_MAX_WEIGHT);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(BOX_MAX_WEIGHT);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt();

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    reportTrackingStatus();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить местоположение отслеживаемых посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите тип посылки:");
        printParcelTypes();
        int type = readInt();
        if (type < 1 || type > 3) {
            System.out.println("Неверный тип посылки.");
            return;
        }

        String description = readText("Введите описание посылки:");
        int weight = readInt("Введите вес посылки (целое число больше 0):", 1, Integer.MAX_VALUE);
        String deliveryAddress = readText("Введите адрес доставки:");
        int sendDay = readInt("Введите день отправки (от 1 до 31):", 1, 31);

        if (type == 1) {
            acceptParcel(new StandardParcel(description, weight, deliveryAddress, sendDay), standardBox);
        } else if (type == 2) {
            FragileParcel parcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
            if (acceptParcel(parcel, fragileBox)) {
                trackableParcels.add(parcel);
            }
        } else {
            int timeToLive = readInt("Введите срок хранения в днях (целое число больше 0):", 1, Integer.MAX_VALUE);
            acceptParcel(new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive),
                    perishableBox);
        }
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int totalCost = 0;
        for (Parcel parcel : allParcels) {
            totalCost += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставки: " + totalCost);
    }

    private static void reportTrackingStatus() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет отправлений с трекингом.");
            return;
        }
        String newLocation = readText("Введите новое местоположение:");
        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(newLocation);
        }
    }

    private static void showBoxContents() {
        System.out.println("Какую коробку показать?");
        printParcelTypes();
        int type = readInt();

        switch (type) {
            case 1:
                printBoxContents(standardBox);
                break;
            case 2:
                printBoxContents(fragileBox);
                break;
            case 3:
                printBoxContents(perishableBox);
                break;
            default:
                System.out.println("Неверный тип коробки.");
        }
    }

    private static void printBoxContents(ParcelBox<? extends Parcel> box) {
        List<? extends Parcel> parcels = box.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }
        System.out.println("В коробке:");
        for (Parcel parcel : parcels) {
            System.out.println("- " + parcel.getDescription());
        }
    }

    //Кладёт посылку в коробку её типа. Посылка принимается к отправке, только если поместилась.
    private static <T extends Parcel> boolean acceptParcel(T parcel, ParcelBox<T> box) {
        if (!box.addParcel(parcel)) {
            return false;
        }
        allParcels.add(parcel);
        System.out.println("Посылка <<" + parcel.getDescription() + ">> добавлена.");
        return true;
    }

    private static void printParcelTypes() {
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");
    }

    // Проверка на пустоту
    private static String readText(String prompt) {
        System.out.println(prompt);
        String text = scanner.nextLine().trim();
        while (text.isEmpty()) {
            System.out.println("Значение не может быть пустым, попробуйте ещё раз:");
            text = scanner.nextLine().trim();
        }
        return text;
    }

    // Проверка на целое число
    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести целое число, попробуйте ещё раз:");
            }
        }
    }

    // Проверка на диапозон
    private static int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        int value = readInt();
        while (value < min || value > max) {
            System.out.println("Значение вне допустимого диапазона, попробуйте ещё раз:");
            value = readInt();
        }
        return value;
    }
}
