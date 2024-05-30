package org.example.handler;

import org.example.Ticket;

import java.util.*;
import java.util.stream.Collectors;

public class CostAnalyzeHandler extends Handler {

    private final String origin;
    private final String destination;

    public CostAnalyzeHandler(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void handleRequest(Object request) {
        if (request instanceof List<?>) {
            List<?> list = (List<?>) request;
            if (!list.isEmpty() && list.get(0) instanceof Ticket) {
                List<Ticket> tickets = (List<Ticket>) list;
                analyzePriceAndMedian(tickets);
            } else {
                System.out.println("Неверный тип данных для анализа разницы цены.");
            }
        } else {
            System.out.println("Неверный тип запроса для анализа разницы цены.");
        }
    }

    private void analyzePriceAndMedian(List<Ticket> tickets) {
        List<Ticket> filteredTickets = tickets.stream()
                .filter(ticket -> origin.equals(ticket.getOrigin()) && destination.equals(ticket.getDestination()))
                .collect(Collectors.toList());

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