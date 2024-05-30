package org.example.handler;

public class HandlerBuilder {
    public Handler build(String origin, String destination) {
        Handler minFlightTimeAnalyzer = new MinFlightAnalyzeHandler(origin, destination);
        Handler priceMedianAnalyzer = new CostAnalyzeHandler(origin, destination);
        Handler converterHandler = new ConverterHandler();
        Handler jsonReaderHandler = new JsonReaderHandler();

        jsonReaderHandler.setNextHandler(converterHandler);
        converterHandler.setNextHandler(minFlightTimeAnalyzer);
        minFlightTimeAnalyzer.setNextHandler(priceMedianAnalyzer);

        return jsonReaderHandler; // Возвращаем первый обработчик в цепочке
    }
}
