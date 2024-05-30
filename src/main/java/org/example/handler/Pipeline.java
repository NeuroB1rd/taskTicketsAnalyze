package org.example.handler;

import org.example.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Pipeline {
    private List<TicketHandler> handlers;
    public Pipeline(){
        handlers = new ArrayList<>();
    }
    public void addHandler(TicketHandler handler){
        handlers.add(handler);
    }
    public void run(List<Ticket> tickets){
        handlers.forEach(x -> x.handleRequest(tickets.stream()));
    }
}
