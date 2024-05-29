package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TicketDTO {
    private String origin;
    private String destination;
    private String departure_date;
    private String departure_time;
    private String arrival_date;
    private String arrival_time;
    private String carrier;
    private int stops;
    private int price;

}
