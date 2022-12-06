package com.imcs.documentcollection.service;

import com.imcs.documentcollection.dto.EmployeeRequest;
import com.imcs.documentcollection.model.Employee;

import javax.transaction.Transactional;
import java.util.List;

public interface EmployeesService {

    List<Employee> getAllEmployees();
    @Transactional
    String createEmployee(EmployeeRequest user) throws Exception;

    @Transactional
    String updateEmployee(EmployeeRequest user) throws Exception;

    boolean deleteEmployee(String id) throws Exception;
    Object updateSSNNumber(long uid,int ssnNumber) throws Exception;
    Object uidVerification(long uid) throws Exception;

}
