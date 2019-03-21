package aze.spring.payroll;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository){
        return args -> {
//            log.info("Preloading " + employeeRepository.save(new Employee("jony", "casher")));
//            log.info("Preloading "+ employeeRepository.save(new Employee("mark", "manager")));
            log.info("Preloading" + orderRepository.save(new Order("MacBook Pro", Status.COMPLETED)));
            log.info("Preloading" + orderRepository.save(new Order("iPhone", Status.IN_PROGRESS)));
            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}
