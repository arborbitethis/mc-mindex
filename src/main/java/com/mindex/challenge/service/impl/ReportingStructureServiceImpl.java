package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.dao.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        Employee populatedEmployee = populateReports(employee);
        int totalReports = countReports(populatedEmployee);
        return new ReportingStructure(populatedEmployee, totalReports);
    }

    private Employee populateReports(Employee employee) {
        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            return employee;
        }

        List<Employee> populatedReports = new ArrayList<>();
        for (Employee report : employee.getDirectReports()) {
            Employee fullReport = employeeRepository.findByEmployeeId(report.getEmployeeId());
            populatedReports.add(populateReports(fullReport));  // Recursively populate reports
        }
        employee.setDirectReports(populatedReports);
        return employee;
    }

    private int countReports(Employee employee) {
        if (employee == null || employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            return 0;
        }

        int total = employee.getDirectReports().size();
        for (Employee report : employee.getDirectReports()) {
            total += countReports(report);
        }
        return total;
    }
}
