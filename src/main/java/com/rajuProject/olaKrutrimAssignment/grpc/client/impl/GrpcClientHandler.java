package com.rajuProject.olaKrutrimAssignment.grpc.client.impl;


import com.rajuProject.olaKrutrimAssignment.exception.ClientHandlerException;
import com.rajuProject.olaKrutrimAssignment.grpc.ProcessRequest;
import com.rajuProject.olaKrutrimAssignment.grpc.ProcessResponse;
import com.rajuProject.olaKrutrimAssignment.grpc.ProcessServiceGrpc;
import com.rajuProject.olaKrutrimAssignment.grpc.client.ClientHandler;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class GrpcClientHandler implements ClientHandler<Integer> {
    private final ProcessServiceGrpc.ProcessServiceStub processServiceStub;


    @Autowired
    public GrpcClientHandler(ProcessServiceGrpc.ProcessServiceStub processServiceStub) {
        this.processServiceStub = processServiceStub;
    }

    @Override
    public void handle(List<Integer> data) throws ClientHandlerException {
        ProcessRequest request = ProcessRequest.newBuilder()
                .addAllNumbers(data)
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        processServiceStub.sendProcessedData(request, new StreamObserver<>() {
            @Override
            public void onNext(ProcessResponse response) {
                System.out.println("Processed number: " + response.getNumber());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("gRPC Error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("gRPC Stream completed");
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ClientHandlerException("gRPC communication interrupted", e);
        }
    }

    @Override
    public String getClientType() {
        return "GRPC";
    }
}