package aze.spring.payroll;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class EmployeeConroller {

    private final EmployeeRepository employeeRepository;
    private final EmployeeResourceAssembler resourceAssembler;

    public EmployeeConroller(EmployeeRepository employeeRepository, EmployeeResourceAssembler resourceAssembler) {
        this.employeeRepository = employeeRepository;
        this.resourceAssembler = resourceAssembler;
    }

    //We create root for project - Javid

    @GetMapping("/employees")
    Resources<Resource<Employee>> all(){
        List<Resource<Employee>> emplorees = employeeRepository.findAll().stream().map(resourceAssembler::toResource).collect(Collectors.toList());

//                employee -> new Resource<>(employee,
//                        linkTo(methodOn(EmployeeConroller.class).one(employee.getId())).withSelfRel(),
//                        linkTo(methodOn(EmployeeConroller.class).all()).withRel("employees"))).collect(Collectors.toList());

        return new Resources<>(emplorees, linkTo(methodOn(EmployeeConroller.class).all()).withSelfRel());
    }

//	return new Resources<>(employees,
//		linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
//}
//
//
//

//    @GetMapping("/employees")
//    List<Employee> all() {
//        return employeeRepository.findAll();
//    }



    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee employee) throws URISyntaxException{
        Resource<Employee> resourse = resourceAssembler.toResource(employeeRepository.save(employee));
        return ResponseEntity.created(new URI(resourse.getId().expand().getHref())).body(resourse);
    }

//    @PostMapping("/employees")
//    Employee newEmployee(@RequestBody Employee newEmployee) {
//        return employeeRepository.save(newEmployee);
//    }

    //we need rest control for single items

    @GetMapping("/employees/{id}")
    Resource<Employee> one(@PathVariable Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException(id)
        );

        return resourceAssembler.toResource(employee);

//        return new Resource<>(employee,
//                linkTo(methodOn(EmployeeConroller.class).one(id)).withSelfRel(),
//                linkTo(methodOn(EmployeeConroller.class).all()).withRel("employees"));

    }


//    @GetMapping("/employees/{id}")
//    Employee one(@PathVariable Integer id) {
//        return employeeRepository.findById(id).orElseThrow(
//                () -> new EmployeeNotFoundException(id));
//
//
//    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id) throws URISyntaxException {
        Employee updatedEmployee = employeeRepository.findById(id).map(
                employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepository.save(employee);
                }).orElseGet(
                () -> {
                   newEmployee.setId(id);
                   return employeeRepository.save(newEmployee);
                });

         Resource<Employee> resource = resourceAssembler.toResource(updatedEmployee);
         return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);


    }


    @DeleteMapping("/employees/{id}")
    ResponseEntity<Employee> deleteEmployee(@PathVariable Integer id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}


