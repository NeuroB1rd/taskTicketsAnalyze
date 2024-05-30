package org.example;

import org.example.handler.*;
import org.example.util.TicketsUtil;
import org.example.util.ValidationUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String filePath = ValidationUtil.validateFilePath(scanner);
        String origin = ValidationUtil.validateCityCode(scanner, "Введите код города отправления: ");
        String destination = ValidationUtil.validateCityCode(scanner, "Введите код города назначения: ");

        Pipeline pipeline = new Pipeline();
        pipeline.addHandler(new MinFlightAnalyzeHandler(origin,destination));
        pipeline.addHandler(new CostAnalyzeHandler(origin,destination));

        pipeline.run(TicketsUtil.convert(TicketsUtil.readFile(filePath)));
    }
}
