package aze.spring.payroll;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Integer id) {
        super("The order is doent exists " + id);
    }
}
