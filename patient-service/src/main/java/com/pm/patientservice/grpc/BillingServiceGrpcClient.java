package com.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    private static final Logger log = LoggerFactory.getLogger(
            BillingServiceGrpcClient.class);

    public  BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ){
            log.info("Connecting to Billing GRPC Service at {}:{}",serverAddress,serverPort);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId,String name,String email){
        BillingRequest billingRequest = BillingRequest.newBuilder().setPatientId(patientId).setEmail(email).setName(name).build();

        BillingResponse response = blockingStub.createBillingAccount(billingRequest);
        log.info("Receive response from billing service via GRPC : {}",response);

        return response;
    }

}
