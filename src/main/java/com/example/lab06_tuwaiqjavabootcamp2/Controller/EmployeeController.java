package com.example.lab06_tuwaiqjavabootcamp2.Controller;

import com.example.lab06_tuwaiqjavabootcamp2.ApiResponse.ApiRespone;
import com.example.lab06_tuwaiqjavabootcamp2.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();


    //1. Get all employees: Retrieves a list of all employees.
    @GetMapping("/get")
    public ResponseEntity getAllEmployee() {
        if (employees.isEmpty())
            return ResponseEntity.status(200).body(new ApiRespone("There are no employees"));
        else
            return ResponseEntity.status(200).body(employees);
    }

    //2. Add a new employee: Adds a new employee to the system.
    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (Employee e : employees) {
            if (e.getId() == employee.getId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiRespone("the employee is already exist."));
            }
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiRespone("Employee added!!"));
    }

    //3. Update an employee: Updates an existing employee's information.
    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable int id, @RequestBody @Valid Employee employee,Errors errors) {
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (int index = 0; index < employees.size(); index++) {
            if (employees.get(index).getId() == id) {
                employees.set(index, employee);
                return ResponseEntity.status(200).body(new ApiRespone("employee was updated !!"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiRespone("employee with id:" + id + ", not found"));
    }

    //4. Delete an employee: Deletes an employee from the system.
    //Note:
    //▪ Verify that the employee exists.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                employees.remove(e);
                return ResponseEntity.status(200).body(new ApiRespone("Employee with ID: " + id + ", is retired now."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiRespone("This ID doesn't exist..."));
    }


    //5. Search Employees by Position: Retrieves a list of employees based on their
    //position (supervisor or coordinator).
    //Note:
    //▪ Ensure that the position parameter is valid (either "supervisor" or "coordinator").
    @GetMapping("/search-position/{position}")
    public ResponseEntity searchEmployeePosition(@PathVariable String position) {
        ArrayList<Employee> positionList = new ArrayList<>();
        if (position.equals("supervisor") || position.equals("coordinator")) {
            for (Employee e : employees) {
                if (e.getPosition().equals(position)) {
                    positionList.add(e);
                }
            }
            if (positionList.isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiRespone("No position with " + position + " is/are found."));
            else
                return ResponseEntity.status(200).body(positionList);
        } else
            return ResponseEntity.status(400).body(new ApiRespone("No position like that..."));
    }


    //6. Get Employees by Age Range: Retrieves a list of employees within a specified
    //age range.
    //Note:
    //▪ Ensure that minAge and maxAge are valid age values.
    @GetMapping("/search-age/{min},{max}")
    public ResponseEntity searchByAge(@PathVariable int min, @PathVariable int max) {
        ArrayList<Employee> ageList = new ArrayList<>();
        if (max >= min && min >= 0) {
            for (Employee e : employees) {
                if (e.getAge() >= min && e.getAge() <= max) {
                    ageList.add(e);
                }
            }
            if (!ageList.isEmpty())
                return ResponseEntity.status(200).body(ageList);
            else
                return ResponseEntity.status(200).body(new ApiRespone("No one with that age range!!"));
        } else
            return ResponseEntity.status(400).body(new ApiRespone("enter age range from to (min to max)"));
    }


    //7. Apply for annual leave: Allow employees to apply for annual leave.
    //Note:
    //▪ Verify that the employee exists.
    //▪ The employee must not be on leave (the onLeave flag must be false).
    //▪ The employee must have at least one day of annual leave remaining.
    //▪ Behavior:
    //▪ Set the onLeave flag to true.
    //▪ Reduce the annualLeave by 1.
    @GetMapping("/apply-annual/{id}")
    public ResponseEntity applyAnnualLeave(@PathVariable int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                if (!e.isOnLeave()) {
                    if (e.getAnnualLeave() > 0) {
                        e.setOnLeave(true);
                        e.setAnnualLeave(e.getAnnualLeave() - 1);
                        return ResponseEntity.status(200).body(new ApiRespone("enjoy your time!"));
                    } else
                        return ResponseEntity.status(400).body(new ApiRespone("You can't take a leave, because you spent all days."));
                } else
                    return ResponseEntity.status(400).body(new ApiRespone("You are already on leave"));
            }
        }
        return ResponseEntity.status(400).body(new ApiRespone("Not found"));
    }

    //8. Get Employees with No Annual Leave: Retrieves a list of employees who have
    //used up all their annual leave.
    @GetMapping("/no-annual")
    public ResponseEntity getEmployeeWithNoAnnualLeave() {
        ArrayList<Employee> annualList = new ArrayList<>();
        if (!employees.isEmpty()){
            for (Employee e : employees) {
                if (e.getAnnualLeave() == 0) {
                    annualList.add(e);
                }
            }
            if (annualList.isEmpty())
                return ResponseEntity.status(400).body(new ApiRespone("every one has annual leave..."));
            else
                return ResponseEntity.status(200).body(annualList);
        }else
            return ResponseEntity.status(400).body(new ApiRespone("No one here"));
    }

    //9. Promote Employee: Allows a supervisor to promote an employee to the position
    //of supervisor if they meet certain criteria.
    // Note:
        //▪ Verify that the employee with the specified ID exists.
        //▪ Ensure that the requester (user making the request) is a supervisor.
        //▪ Validate that the employee's age is at least 30 years.
        //▪ Confirm that the employee is not currently on leave.
        //▪ Change the employee's position to "supervisor" if they meet the criteria.
    @PutMapping("/promote/{id},{employeeId}")
    public ResponseEntity promoteEmployee(@PathVariable int id,@PathVariable int employeeId){
        for (Employee e:employees){
            if (e.getId()==id){
                if (e.getPosition().equals("supervisor")){
                    for (Employee e2:employees){
                        if (e2.getId()==employeeId){
                            if (e2.getAge()>=30&&!e2.isOnLeave()){
                                e2.setPosition("supervisor");
                                return ResponseEntity.status(200).body(new ApiRespone("Congratulation welcome supervisor."+e2.getName()));
                            }else
                                return ResponseEntity.status(400).body(new ApiRespone("you're not compatible for promotion!"));
                        }
                    }
                    return ResponseEntity.status(400).body(new ApiRespone("Employee doesn't exist."));
                }else
                    return ResponseEntity.status(400).body(new ApiRespone("you don't have permission to promote employees"));
            }
        }
        if (employees.isEmpty())
            return ResponseEntity.status(400).body(new ApiRespone("No one here"));
        else
            return ResponseEntity.status(400).body(new ApiRespone("Supervisor doesn't exist."));
    }


}
