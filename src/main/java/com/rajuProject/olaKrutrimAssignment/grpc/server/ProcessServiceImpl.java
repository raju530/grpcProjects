package com.rajuProject.olaKrutrimAssignment.grpc.server;


import com.rajuProject.olaKrutrimAssignment.grpc.ProcessRequest;
import com.rajuProject.olaKrutrimAssignment.grpc.ProcessResponse;
import com.rajuProject.olaKrutrimAssignment.grpc.ProcessServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class ProcessServiceImpl extends ProcessServiceGrpc.ProcessServiceImplBase {

    @Override
    public void sendProcessedData(ProcessRequest request,
                                  StreamObserver<ProcessResponse> responseObserver) {
        request.getNumbersList().forEach(number -> {
            ProcessResponse response = ProcessResponse.newBuilder()
                    .setNumber(number)
                    .build();
            responseObserver.onNext(response);
        });
        responseObserver.onCompleted();
    }
}
