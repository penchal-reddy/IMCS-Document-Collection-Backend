package com.imcs.documentcollection.service.impl;

import com.google.gson.JsonObject;
import com.imcs.documentcollection.dto.EmployeeRequest;
import com.imcs.documentcollection.model.Employee;
import com.imcs.documentcollection.repository.EmployeesRepository;
import com.imcs.documentcollection.service.EmployeesService;
import com.imcs.documentcollection.service.IEmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeesService {

    @Value("${app.baseurl}")
    private String baseUrl;

    @Value("${uid.expiry.hours}")
    private long uidExpiryhours;
    @Autowired
    EmployeesRepository employeeRepository;

    @Autowired
    IEmailService emailService;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public String createEmployee(EmployeeRequest employee) throws Exception {
        Employee newEmployee = new Employee();
        BeanUtils.copyProperties(employee, newEmployee);
        newEmployee.setUid(gererateUid());
        newEmployee.setCreatedDate(getCSTDate());
        final long hoursInMillis = 60L * 60L * 1000L;
        newEmployee.setExpiryDate(new Date(getCSTDate().getTime() + (uidExpiryhours * hoursInMillis)));
        employeeRepository.save(newEmployee);
        String emailSubject = "SSN Number Request Notification";
        String link = baseUrl+"/"+newEmployee.getUid();
        String emailContent = "Dear " + employee.getFirstName() + " " + employee.getLastName() + ",\n\n" + "Your account has been created for IMCS Document Collection portal. Please use below link for SSN Number enter details.\n"+link;
        emailService.sendEmail(newEmployee.getEmail(), "", "", emailSubject, emailContent, "text/plain");
        return "User Registration Success";
    }

    @Override
    public String updateEmployee(EmployeeRequest employee) throws Exception {
        if (employee.getId() != 0) {
            Employee empFromDb=employeeRepository.findById(employee.getId()).get();
            BeanUtils.copyProperties(employee, empFromDb);
            employeeRepository.save(empFromDb);
            return "User Updated Success";
        }
        return "User Updated Failed";
    }

    @Override
    public boolean deleteEmployee(String id) throws Exception {
        return false;
    }

    @Override
    public Object updateSSNNumber(long uid,int ssnNumber) throws Exception {
        if (uid!= 0) {
           Employee empFromDb=employeeRepository.findByUid(uid).get();
            if(empFromDb!=null && empFromDb.getEmail() !=null){
                empFromDb.setSsnNumber(ssnNumber);
                employeeRepository.save(empFromDb);
                return true;
            }
        }
        return false;
    }

    @Override
    public Object uidVerification(long uid) throws Exception {
        Map<String,Object> response=new HashMap<>();
        response.put("status",false);
        response.put("message","Invalid UID");
        if (uid!= 0) {
            Employee empFromDb=employeeRepository.findByUid(uid).get();
            if(empFromDb!=null){
                if(getCSTDate().after(empFromDb.getExpiryDate())){
                    response.put("status",false);
                    response.put("message","UID Expired");
                }else{
                    response.put("status",true);
                    response.put("message","valid UID");
                    response.put("first_name",empFromDb.getFirstName());
                    response.put("middle_name",empFromDb.getMiddleName());
                    response.put("last_name",empFromDb.getLastName());
                    response.put("email",empFromDb.getEmail());
                }
            }
        }
        return response;
    }

    public Date getCSTDate() throws Exception{
        ZoneId zone = ZoneId.of("America/Chicago");
        ZonedDateTime cstTime = ZonedDateTime.now(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateInCst = cstTime.format(formatter);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentDateInCst);
    }

    public long gererateUid(){
        final long MAX_NUMBER_YOU_WANT_TO_HAVE = 9999999999999999L;
        final long MIN_NUMBER_YOU_WANT_TO_HAVE = 1000000000000000L;
        return Long.valueOf(Math.abs(Float.valueOf(new Random().nextFloat() * (MAX_NUMBER_YOU_WANT_TO_HAVE - MIN_NUMBER_YOU_WANT_TO_HAVE)).longValue()));
    }

}
