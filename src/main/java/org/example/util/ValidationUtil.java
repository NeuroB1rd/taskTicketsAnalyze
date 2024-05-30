package org.example.util;

import java.util.Scanner;

public final class ValidationUtil {
    private ValidationUtil(){

    }
    public static String validateFilePath(Scanner scanner) {
        String filePath = "";
        while (filePath.isEmpty()) {
            System.out.print("Введите путь к JSON файлу: ");
            filePath = scanner.nextLine().trim();
            if (filePath.isEmpty()) {
                System.out.println("Путь к файлу не может быть пустым.");
            }
        }
        return filePath;
    }

    public static String validateCityCode(Scanner scanner, String prompt) {
        String cityCode = "";
        while (cityCode.isEmpty()) {
            System.out.print(prompt);
            cityCode = scanner.nextLine().trim();
            if (cityCode.isEmpty()) {
                System.out.println("Код города не может быть пустым.");
            }
        }
        return cityCode;
    }
}
