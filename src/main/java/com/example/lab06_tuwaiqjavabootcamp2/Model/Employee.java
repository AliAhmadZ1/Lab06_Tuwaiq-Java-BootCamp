package com.example.lab06_tuwaiqjavabootcamp2.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {

    //ID , name, email , phoneNumber ,age, position, onLeave, hireDate and
    //annualLeave.


    private int id;
    private String name;
    private String email;
    private int phoneNumber;
    private int age;
    private String position;
    private boolean onLeave;
    private LocalDate hireDate;
    private int annualLeave;


    //JSON ------------------------------------------*****------------------------------------------
    //  {
    //    "id": 1,
    //    "name": "dflkmjbx",
    //    "email": "erd84965drtcghvnbtcgf",
    //    "phoneNumber": 55,
    //    "age": 20,
    //    "position": "dsgfb",
    //    "onLeave": false,
    //    "hireDate": "2000-05-07",
    //    "annualLeave": 55
    //  }

}
