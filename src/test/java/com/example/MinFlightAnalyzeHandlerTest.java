package com.example;

import org.example.Ticket;
import org.example.handler.CostAnalyzeHandler;
import org.example.handler.MinFlightAnalyzeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MinFlightAnalyzeHandlerTest {

    private List<Ticket> tickets;

    @BeforeEach
    public void setUp() {
        tickets = Arrays.asList(
                new Ticket("VVO", "TLV", LocalDateTime.of(2018, 5, 12, 16, 20), LocalDateTime.of(2018, 5, 12, 22, 10),"TK",1,1),
                new Ticket("VVO", "TLV", LocalDateTime.of(2018, 5, 12, 17, 20), LocalDateTime.of(2018, 5, 12, 23, 50),"S7",1,1)
        );
    }

    @Test
    public void testAnalyzeMinFlightTimes() {
        MinFlightAnalyzeHandler handler = new MinFlightAnalyzeHandler("VVO", "TLV");
        handler.handleRequest(tickets.stream());

        Map<String, Duration> minFlightTimes = handler.getMinFlightTimes();
        assertNotNull(minFlightTimes);
        assertEquals(2, minFlightTimes.size());
        assertEquals(Duration.ofHours(5).plusMinutes(50), minFlightTimes.get("TK"));
        assertEquals(Duration.ofHours(6).plusMinutes(30), minFlightTimes.get("S7"));
    }

    @Test
    public void testHandleRequest_NullInput() {
        MinFlightAnalyzeHandler handler = new MinFlightAnalyzeHandler("VVO", "TLV");
        assertDoesNotThrow(() -> handler.handleRequest(null));
    }
}
