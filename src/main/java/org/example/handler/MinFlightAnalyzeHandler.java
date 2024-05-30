package org.example.handler;

import org.example.Ticket;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class MinFlightAnalyzeHandler extends Handler {
    private final String origin;
    private final String destination;

    public MinFlightAnalyzeHandler(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void handleRequest(Object request) {
        if (request instanceof List<?>) {
            List<?> list = (List<?>) request;
            if (!list.isEmpty() && list.get(0) instanceof Ticket) {
                List<Ticket> tickets = (List<Ticket>) list;
                analyzeMinFlightTimes(tickets);
            } else {
                System.out.println("Неверный тип данных для анализа минимального времени перелета.");
            }
        } else {
            System.out.println("Неверный тип запроса для анализа минимального времени перелета.");
        }
    }

    private void analyzeMinFlightTimes(List<Ticket> tickets) {
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
    }
}