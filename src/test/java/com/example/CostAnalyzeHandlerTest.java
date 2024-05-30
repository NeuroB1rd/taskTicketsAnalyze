package com.example;

import org.example.Ticket;
import org.example.handler.CostAnalyzeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CostAnalyzeHandlerTest {

    private List<Ticket> tickets;

    @BeforeEach
    public void setUp() {
        tickets = Arrays.asList(
                new Ticket("VVO", "TLV", LocalDateTime.of(2018, 5, 12, 16, 20), LocalDateTime.of(2018, 5, 12, 22, 10), "TK",1, 12400),
                new Ticket("VVO", "TLV", LocalDateTime.of(2018, 5, 12, 16, 20), LocalDateTime.of(2018, 5, 12, 22, 10), "S7",1, 13100),
                new Ticket("VVO", "TLV", LocalDateTime.of(2018, 5, 12, 16, 20), LocalDateTime.of(2018, 5, 12, 22, 10), "UA",1, 13000),
                new Ticket("VVO", "TLV", LocalDateTime.of(2018, 5, 12, 16, 20), LocalDateTime.of(2018, 5, 12, 22, 10), "SU",1, 11000),
                new Ticket("VVO", "TLV", LocalDateTime.of(2018, 5, 12, 16, 20), LocalDateTime.of(2018, 5, 12, 22, 10), "BA",1, 14500),
                new Ticket("VVO", "TLV", LocalDateTime.of(2018, 5, 12, 16, 20), LocalDateTime.of(2018, 5, 12, 22, 10), "LH",1, 16100)
        );
    }

    @Test
    public void testAnalyzePriceAndMedian() {
        CostAnalyzeHandler handler = new CostAnalyzeHandler("VVO", "TLV");
        handler.handleRequest(tickets.stream());

        double priceMedianDifference = handler.getPriceMedianDifference();
        assertEquals(300, priceMedianDifference);
    }
}
