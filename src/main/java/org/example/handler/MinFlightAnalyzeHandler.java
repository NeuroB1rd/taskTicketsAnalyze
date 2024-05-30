package org.example.handler;

import org.example.Ticket;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinFlightAnalyzeHandler implements TicketHandler {
    private final String origin;
    private final String destination;
    private Map<String, Duration> minFlightTimes;

    public MinFlightAnalyzeHandler(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void handleRequest(Stream<Ticket> stream) {
        analyzeMinFlightTimes(stream);
        printMinFlightTimes();
    }

    private void analyzeMinFlightTimes(Stream<Ticket> ticketStream) {
        List<Ticket> filteredTickets = ticketStream
                .filter(ticket -> origin.equals(ticket.getOrigin()) && destination.equals(ticket.getDestination()))
                .collect(Collectors.toList());

        Map<String, List<Ticket>> ticketsByCarrier = filteredTickets.stream()
                .collect(Collectors.groupingBy(Ticket::getCarrier));

        minFlightTimes = ticketsByCarrier.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(ticket -> Duration.between(ticket.getDepartureDateTime(), ticket.getArrivalDateTime()))
                                .min(Comparator.naturalOrder())
                                .orElse(Duration.ZERO)
                ));
    }

    private void printMinFlightTimes() {
        System.out.println("Минимальное время полета между " + origin + " и " + destination + " для каждого авиаперевозчика:");
        for (Map.Entry<String, Duration> entry : minFlightTimes.entrySet()) {
            Duration duration = entry.getValue();
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            System.out.println(entry.getKey() + ": " + hours + " часов " + minutes + " минут");
        }
    }

    public Map<String, Duration> getMinFlightTimes() {
        return minFlightTimes;
    }
}