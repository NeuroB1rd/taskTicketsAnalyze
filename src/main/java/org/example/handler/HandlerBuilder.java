package org.example.handler;

public class HandlerBuilder {
    public Handler build(String origin, String destination) {
        Handler jsonReaderHandler = new JsonReaderHandler();
        Handler converterHandler = new ConverterHandler();
        Handler analyzerHandler = new AnalyzerHandler(origin, destination);

        jsonReaderHandler.setNextHandler(converterHandler);
        converterHandler.setNextHandler(analyzerHandler);

        return jsonReaderHandler;
    }
}
