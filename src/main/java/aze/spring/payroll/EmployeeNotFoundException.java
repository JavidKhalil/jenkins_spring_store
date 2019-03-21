package aze.spring.payroll;

public class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(Integer id){
        super("Cound`t find an Employee with id " + id);
    }
}
