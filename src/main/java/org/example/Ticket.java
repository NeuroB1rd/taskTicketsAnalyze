package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Ticket {
    private String origin;
    private String destination;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String carrier;
    private int stops;
    private int price;
}
