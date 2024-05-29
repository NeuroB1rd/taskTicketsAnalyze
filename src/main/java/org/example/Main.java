package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к JSON файлу: ");
        String filePath = scanner.nextLine();

        System.out.print("Введите код города отправления: ");
        String origin = scanner.nextLine();

        System.out.print("Введите код города назначения: ");
        String destination = scanner.nextLine();

        Handler jsonReaderHandler = new JsonReaderHandler();
        Handler converterHandler = new ConverterHandler();
        Handler analyzerHandler = new AnalyzerHandler(origin, destination);

        jsonReaderHandler.setNextHandler(converterHandler);
        converterHandler.setNextHandler(analyzerHandler);

        jsonReaderHandler.handleRequest(filePath);
    }
}
