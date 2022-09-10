package com.illu.ilk.services;

import com.illu.ilk.employee.Empty;
import com.illu.ilk.employee.EmployeeResponseList;
import com.illu.ilk.employee.EmployeeRequest;
import com.illu.ilk.employee.EmployeeResponse;
import com.illu.ilk.employee.EmployeeDetail;
import com.illu.ilk.employee.EmployeeServiceGrpc;
import io.grpc.stub.StreamObserver;
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
}