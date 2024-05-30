package org.example;

import org.example.handler.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath = "";
        String origin = "";
        String destination = "";

        while (filePath.isEmpty()) {
            System.out.print("Введите путь к JSON файлу: ");
            filePath = scanner.nextLine().trim();
            if (filePath.isEmpty()) {
                System.out.println("Путь к файлу не может быть пустым.");
            }
        }

        while (origin.isEmpty()) {
            System.out.print("Введите код города отправления: ");
            origin = scanner.nextLine().trim();
            if (origin.isEmpty()) {
                System.out.println("Код города отправления не может быть пустым.");
            }
        }

        while (destination.isEmpty()) {
            System.out.print("Введите код города назначения: ");
            destination = scanner.nextLine().trim();
            if (destination.isEmpty()) {
                System.out.println("Код города назначения не может быть пустым.");
            }
        }

        HandlerBuilder chainBuilder = new HandlerBuilder();
        Handler handlerChain = chainBuilder.build(origin, destination);

        handlerChain.handleRequest(filePath);
    }
}
