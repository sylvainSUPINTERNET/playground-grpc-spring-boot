package com.illu.ilk.services;

import com.illu.ilk.employee.Empty;
import com.illu.ilk.employee.Stock;
import com.illu.ilk.employee.EmployeeResponseList;
import com.illu.ilk.employee.EmployeeRequest;
import com.illu.ilk.employee.EmployeeResponse;
import com.illu.ilk.employee.EmployeeDetail;
import com.illu.ilk.employee.EmployeeServiceGrpc;
import io.grpc.stub.StreamObserver;
import com.illu.ilk.employee.StockDetail;

import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    /**
     * Unary operation to get the employee based on employee id
     * @param request
     * @param responseObserver
     */
    @Override
    public void getEmployee(EmployeeRequest request,
                            StreamObserver<EmployeeResponse> responseObserver) {

        //call repository to load the data from database
        //we have added static data for example
        EmployeeResponse empResp = EmployeeResponse
                .newBuilder()
                .setEmpId(request.getEmpId())
                .setName("abc")
                .build();

        //set the response object
        responseObserver.onNext(empResp);

        //mark process is completed
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployees(Empty request,
    StreamObserver<EmployeeResponseList> responseObserver) {

        responseObserver.onNext(
            EmployeeResponseList.newBuilder()
            .addEmployee(EmployeeDetail.newBuilder().setName("abc").build())
            .addEmployee(EmployeeDetail.newBuilder().setName("tot").build())
            .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getStock(Empty request, StreamObserver<Stock> responseObserver) {

        try {
            for (int i=0;i<10;i++) {
                responseObserver.onNext(Stock.newBuilder().setId(i).build());
                Thread.sleep(2000);
            }
        } catch ( Exception e ) {
            responseObserver.onError(e);
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Stock> streamGetStock(StreamObserver<StockDetail> responseObserver ) {
        return new StreamObserver<Stock>() {
            @Override
            public void onNext(Stock value) {
                System.out.println(value);
                responseObserver.onNext(StockDetail.newBuilder().setId(value.getId()).build());
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    
}