package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConverterHandler extends Handler {
    @Override
    public void handleRequest(Object request) {
        if (request instanceof TicketsDTO) {
            TicketsDTO ticketsDTO = (TicketsDTO) request;
            List<TicketDTO> ticketDTOList = ticketsDTO.getTickets();
            List<Ticket> ticketList = ticketDTOList.stream()
                    .map(this::convertToTicket)
                    .toList();
            if (nextHandler != null) {
                nextHandler.handleRequest(ticketList);
            }
        } else {
            System.out.println("Неверный тип запроса для конвертации DTO в сущности.");
        }
    }

    private Ticket convertToTicket(TicketDTO ticketDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        String departureTime = ticketDTO.getDeparture_time().length() == 4 ? "0" + ticketDTO.getDeparture_time() : ticketDTO.getDeparture_time();
        String arrivalTime = ticketDTO.getArrival_time().length() == 4 ? "0" + ticketDTO.getArrival_time() : ticketDTO.getArrival_time();

        LocalDateTime departureDateTime = LocalDateTime.parse(ticketDTO.getDeparture_date() + " " + departureTime, formatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse(ticketDTO.getArrival_date() + " " + arrivalTime, formatter);
        return new Ticket(ticketDTO.getOrigin(), ticketDTO.getDestination(), departureDateTime, arrivalDateTime, ticketDTO.getCarrier(), ticketDTO.getStops(), ticketDTO.getPrice());
    }
}
