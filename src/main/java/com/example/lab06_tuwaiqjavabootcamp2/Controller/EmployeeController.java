package com.example.lab06_tuwaiqjavabootcamp2.Controller;

import com.example.lab06_tuwaiqjavabootcamp2.ApiResponse.ApiRespone;
import com.example.lab06_tuwaiqjavabootcamp2.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity getAllEmployee(){
        if (employees.isEmpty())
            return ResponseEntity.status(200).body(new ApiRespone("There are no employees"));
        else
            return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee){
        for (Employee e: employees) {
            if (e.getId()==employee.getId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiRespone("the employee is already exist."));
            }
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiRespone("Employee added!!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable int id,@RequestBody @Valid Employee employee){
        for (int index=0;index<employees.size();index++){
            if (employees.get(index).getId()==id){
                employees.set(index,employee);
                return ResponseEntity.status(200).body(new ApiRespone("employee was updated !!"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiRespone("employee with id:"+id+", not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id){
        for (Employee e: employees) {
            if (e.getId()==id) {
                employees.remove(e);
                return ResponseEntity.status(200).body(new ApiRespone("Employee with ID: " + id + ", is retired now."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiRespone("This ID doesn't exist..."));
    }





}
