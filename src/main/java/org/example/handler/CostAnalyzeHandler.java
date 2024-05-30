package org.example.handler;

import org.example.Ticket;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CostAnalyzeHandler implements TicketHandler {
    private final String origin;
    private final String destination;
    private double priceMedianDifference;

    public CostAnalyzeHandler(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void handleRequest(Stream<Ticket> stream) {
        analyzePriceAndMedian(stream);
        printPriceMedianDifference();
    }

    private void analyzePriceAndMedian(Stream<Ticket> ticketStream) {
        List<Integer> prices = ticketStream
                .filter(ticket -> origin.equals(ticket.getOrigin()) && destination.equals(ticket.getDestination()))
                .map(Ticket::getPrice)
                .sorted()
                .collect(Collectors.toList());

        double averagePrice = prices.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

        double medianPrice = calculateMedian(prices);

        priceMedianDifference = averagePrice - medianPrice;
    }

    private void printPriceMedianDifference() {
        System.out.println("Разница между средней ценой и медианой для полетов между " + origin + " и " + destination + ": " + priceMedianDifference);
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

    public double getPriceMedianDifference() {
        return priceMedianDifference;
    }
}