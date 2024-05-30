package org.example.handler;

import org.example.Ticket;

import java.util.stream.Stream;

public interface TicketHandler {
    void handleRequest(Stream<Ticket> stream);
}
