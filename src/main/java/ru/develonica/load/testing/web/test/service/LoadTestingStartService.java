package ru.develonica.load.testing.web.test.service;

import com.google.rpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.develonica.load.testing.common.model.generated.*;
import ru.develonica.load.testing.common.service.LoadTestingServiceGrpc;
import ru.develonica.load.testing.web.test.model.exception.LoadTestingStartException;
import ru.develonica.load.testing.web.test.model.request.LoadTestingStartParams;
import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;

@Service
public class LoadTestingStartService {
    private static final Logger LOG = LoggerFactory.getLogger(LoadTestingStartService.class);

    @GrpcClient("load-testing-start")
    private LoadTestingServiceGrpc.LoadTestingServiceBlockingStub loadTestingStub;



    public String startLoadTestingCase(LoadTestingStartParams params, TestCaseRequest testCaseRequest) throws LoadTestingStartException {
        LoadTestingStartRequest.Builder request = LoadTestingStartRequest.newBuilder();
        request.setDuration(params.getDuration().toString());
        request.setParallelRequests(params.getParallelRequests());
        request.setUrl(testCaseRequest.getUrl());
        request.setBody(testCaseRequest.getBody());
        request.setMethod(Method.valueOf(testCaseRequest.getMethod().getName()));
        request.putAllHeader(testCaseRequest.getHeader());
        request.setJmxHost(params.getJmxHost());
        request.setJmxPort(params.getJmxPort());

        try {
            LoadTestingStartResponse response = loadTestingStub.start(request.build());
            LOG.info("Response from grpc/start {}", response.getStatus());
            return response.getStatus().name();
        } catch (StatusRuntimeException sre) {
            Status status = StatusProto.fromThrowable(sre);
            LOG.error("Error while receiving grpc response: status: {} message: {}", status, sre.getMessage());
            throw new LoadTestingStartException(sre);
        }
    }

    public Metrics getMetrics() throws LoadTestingStartException {
        Empty empty = Empty.newBuilder().build();

        try {
            Metrics metrics = loadTestingStub.metrics(empty);
            return metrics;
        } catch (StatusRuntimeException sre) {
            Status status = StatusProto.fromThrowable(sre);
            LOG.error("Error while receiving grpc response: status: {} message: {}", status, sre.getMessage());
            throw new LoadTestingStartException(sre);
        }
    }
}
