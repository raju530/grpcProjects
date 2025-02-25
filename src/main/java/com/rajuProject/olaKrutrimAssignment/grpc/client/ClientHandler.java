package com.rajuProject.olaKrutrimAssignment.grpc.client;


import com.rajuProject.olaKrutrimAssignment.exception.ClientHandlerException;

import java.util.List;

public interface ClientHandler<T> {
    void handle(List<T> data) throws ClientHandlerException;
    String getClientType();
}
