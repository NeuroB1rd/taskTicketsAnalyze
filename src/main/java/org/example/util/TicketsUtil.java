package org.example.util;

import com.google.gson.Gson;
import org.example.Ticket;
import org.example.dto.TicketsDTO;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class TicketsUtil {
    private TicketsUtil(){

    }
    public static TicketsDTO readFile(String filepath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            TicketsDTO ticketsDTO = gson.fromJson(reader, TicketsDTO.class);
            if (ticketsDTO == null) {
                throw(new Exception("Пустой список"));
            }
            return ticketsDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return new TicketsDTO();
        }
    }
    public static List<Ticket> convert(TicketsDTO ticketsDTO){
        return Optional.ofNullable(ticketsDTO)
                .map(TicketsDTO::getTickets)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(
                ticketDTO -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

                    String departureTime = ticketDTO.getDeparture_time().length() == 4 ? "0" + ticketDTO.getDeparture_time() : ticketDTO.getDeparture_time();
                    String arrivalTime = ticketDTO.getArrival_time().length() == 4 ? "0" + ticketDTO.getArrival_time() : ticketDTO.getArrival_time();

                    LocalDateTime departureDateTime = LocalDateTime.parse(ticketDTO.getDeparture_date() + " " + departureTime, formatter);
                    LocalDateTime arrivalDateTime = LocalDateTime.parse(ticketDTO.getArrival_date() + " " + arrivalTime, formatter);
                    return new Ticket(ticketDTO.getOrigin(), ticketDTO.getDestination(), departureDateTime, arrivalDateTime, ticketDTO.getCarrier(), ticketDTO.getStops(), ticketDTO.getPrice());
                }
        ).collect(Collectors.toList());
    }
}
