package aze.spring.payroll;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CUSTOMER_ORDER")
public class Order {

    @Id
    @GeneratedValue
    private Integer id;

    private Status status;
    private String description;

    public Order(){

    }

    public Order(String description, Status status){
        this.description = description;
        this.status = status;
    }

}

