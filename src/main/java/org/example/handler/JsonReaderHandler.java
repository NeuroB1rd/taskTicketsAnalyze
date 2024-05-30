package org.example.handler;

import com.google.gson.Gson;
import org.example.dto.TicketsDTO;

import java.io.FileReader;

public class JsonReaderHandler extends Handler {
    @Override
    public void handleRequest(Object request) {
        if (request instanceof String) {
            String filePath = (String) request;
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(filePath)) {
                TicketsDTO ticketsDTO = gson.fromJson(reader, TicketsDTO.class);
                if (ticketsDTO != null && nextHandler != null) {
                    nextHandler.handleRequest(ticketsDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Неверный тип запроса для чтения JSON.");
        }
    }
}
