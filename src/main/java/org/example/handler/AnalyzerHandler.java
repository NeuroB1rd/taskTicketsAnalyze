package org.example.handler;

import org.example.Ticket;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyzerHandler extends Handler {
    private final String origin;
    private final String destination;

    public AnalyzerHandler(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void handleRequest(Object request) {
        if (request instanceof List<?>) {
            List<?> list = (List<?>) request;
            if (!list.isEmpty() && list.get(0) instanceof Ticket) {
                List<Ticket> tickets = (List<Ticket>) list;
                analyzeTickets(tickets);
            } else {
                System.out.println("Неверный тип данных для анализа.");
            }
        } else {
            System.out.println("Неверный тип запроса для анализа данных.");
        }
    }

    private void analyzeTickets(List<Ticket> tickets) {
        List<Ticket> filteredTickets = tickets.stream()
                .filter(ticket -> origin.equals(ticket.getOrigin()) && destination.equals(ticket.getDestination()))
                .collect(Collectors.toList());

        Map<String, List<Ticket>> ticketsByCarrier = filteredTickets.stream()
                .collect(Collectors.groupingBy(Ticket::getCarrier));

        Map<String, Duration> minFlightTimes = new HashMap<>();
        for (Map.Entry<String, List<Ticket>> entry : ticketsByCarrier.entrySet()) {
            String carrier = entry.getKey();
            List<Ticket> carrierTickets = entry.getValue();

            Optional<Duration> minDuration = carrierTickets.stream()
                    .map(ticket -> Duration.between(ticket.getDepartureDateTime(), ticket.getArrivalDateTime()))
                    .min(Comparator.naturalOrder());

            minDuration.ifPresent(duration -> minFlightTimes.put(carrier, duration));
        }

        System.out.println("Минимальное время полета между " + origin + " и " + destination + " для каждого авиаперевозчика:");
        for (Map.Entry<String, Duration> entry : minFlightTimes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toHours() + " часов " + (entry.getValue().toMinutes() % 60) + " минут");
        }

        List<Integer> prices = filteredTickets.stream()
                .map(Ticket::getPrice)
                .sorted()
                .collect(Collectors.toList());

        double averagePrice = prices.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

        double medianPrice = calculateMedian(prices);

        System.out.println("Разница между средней ценой и медианой для полетов между " + origin + " и " + destination + ": " + (averagePrice - medianPrice));
    }

    private double calculateMedian(List<Integer> prices) {
        int size = prices.size();
        if (size == 0) {
            return 0;
        } else if (size % 2 == 1) {
            return prices.get(size / 2);
        } else {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        }
    }
}