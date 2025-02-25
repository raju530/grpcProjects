package com.rajuProject.olaKrutrimAssignment.grpc.client;


import com.rajuProject.olaKrutrimAssignment.grpc.client.ClientHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ClientHandlerFactory {

    private final Map<String, ClientHandler<?>> handlers;

    @Autowired
    public ClientHandlerFactory(List<ClientHandler<?>> handlerList) {
        handlers = handlerList.stream()
                .collect(Collectors.toMap(
                        ClientHandler::getClientType,
                        Function.identity()
                ));
    }

    @SuppressWarnings("unchecked")
    public <T> ClientHandler<T> getHandler(String clientType) {
        ClientHandler<?> handler = handlers.get(clientType.toUpperCase());
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for type: " + clientType);
        }
        return (ClientHandler<T>) handler;
    }
}