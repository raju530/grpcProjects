package com.rajuProject.olaKrutrimAssignment.config;

import com.rajuProject.olaKrutrimAssignment.grpc.ProcessServiceGrpc;
import org.springframework.context.annotation.Configuration;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;

@Configuration
public class GrpcClientConfig {

    @GrpcClient("process-service")
    private Channel clientChannel;

    @Bean
    public ProcessServiceGrpc.ProcessServiceStub processServiceStub() {
        return ProcessServiceGrpc.newStub(clientChannel);
    }
}
