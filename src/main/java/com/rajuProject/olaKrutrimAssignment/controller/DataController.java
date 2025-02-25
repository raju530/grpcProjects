package com.rajuProject.olaKrutrimAssignment.controller;

import com.rajuProject.olaKrutrimAssignment.dto.ProcessDataRequest;
import com.rajuProject.olaKrutrimAssignment.grpc.client.ClientHandler;
import com.rajuProject.olaKrutrimAssignment.grpc.client.ClientHandlerFactory;
import com.rajuProject.olaKrutrimAssignment.service.processor.DataProcessor;
import com.rajuProject.olaKrutrimAssignment.service.processor.ProcessorFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {

    private final ProcessorFactory processorFactory;
    private final ClientHandlerFactory clientHandlerFactory;

    @Autowired
    public DataController(ProcessorFactory processorFactory,
                          ClientHandlerFactory clientHandlerFactory) {
        this.processorFactory = processorFactory;
        this.clientHandlerFactory = clientHandlerFactory;
    }

    @PostMapping("/processData")
    public ResponseEntity<?> processData(
            @RequestBody @Valid ProcessDataRequest<?> request,
            @RequestParam(defaultValue = "GRPC") String clientType
    ) {
        try {
            if (request.data().isEmpty()) {
                return ResponseEntity.badRequest().body("Input data cannot be empty");
            }

            Class<?> dataType = request.data().get(0).getClass();
            DataProcessor<Object> processor = (DataProcessor<Object>) processorFactory.getProcessor(dataType);
            ClientHandler<Object> handler = clientHandlerFactory.getHandler(clientType);

            List<Object> processed = processor.process((List<Object>) request.data());
            handler.handle(processed);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "processedCount", processed.size()
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Processing failed: " + e.getMessage());
        }
    }
}