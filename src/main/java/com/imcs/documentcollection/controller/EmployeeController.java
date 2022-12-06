package com.imcs.documentcollection.controller;

import com.imcs.documentcollection.dto.EmployeeRequest;
import com.imcs.documentcollection.model.Employee;
import com.imcs.documentcollection.model.UserProfile;
import com.imcs.documentcollection.model.UserRequest;
import com.imcs.documentcollection.service.EmployeesService;
import com.imcs.documentcollection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeesService employeesService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody EmployeeRequest employee, Authentication authentication) throws Exception {
        return ResponseEntity.ok(employeesService.createEmployee(employee));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllUsers() {
        return ResponseEntity.ok(employeesService.getAllEmployees());
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody EmployeeRequest employee, Authentication authentication) throws Exception {
        return ResponseEntity.ok(employeesService.updateEmployee(employee));
    }

    @GetMapping(path="ssnRequest/{uid}")
    public ResponseEntity<Object> updateMetadata (@PathVariable long uid,@RequestParam(required = true) int ssnNumber) throws Exception {
        return ResponseEntity.ok(employeesService.updateSSNNumber(uid,ssnNumber));
    }

    @GetMapping(path="uidVerification/{uid}")
    public ResponseEntity<Object> uidVerification (@PathVariable long uid) throws Exception {
        return ResponseEntity.ok(employeesService.uidVerification(uid));
    }

}
