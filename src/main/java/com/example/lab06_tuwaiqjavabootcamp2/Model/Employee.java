package com.example.lab06_tuwaiqjavabootcamp2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {

    //ID , name, email , phoneNumber ,age, position, onLeave, hireDate and
    //annualLeave.

    //Validation: 
    //▪ ID: 
        //- Cannot be null. 
        //- Length must be more than 2 characters. 
    @NotNull(message = "Shouldn't be empty")
    @Min(value = 10,message = "must be 2 digit")
//    @Pattern(regexp = "^[0-9][a-zA-Z]{2}$")
    private int id;

    //▪ Name: 
        //- Cannot be null. 
        //- Length must be more than 4 characters. 
        //- Must contain only characters (no numbers).
    @NotNull(message = "Shouldn't be empty")
    @Size(min = 4,message = "Length must be more than 4 characters.")
    @Pattern(regexp = "^[A-Za-z]+$",message = "Only letters, number not allowed.")
    private String name;

    //▪ Email: 
        //- Must be a valid email format. 
    @Email
    private String email;

    //▪ Phone Number: 
        //- Must start with "05". 
        //- Must consists of exactly 10 digits.
    @Pattern(regexp = "^05\\d{8}$")
    @Digits(integer = 10, fraction = 0,message = "Should be exactly 10 digits.")
    private String phoneNumber;

    //▪ Age:                                
        //- Cannot be null. 
        //- Must be a number. 
        //- Must be more than 25.
    @NotNull(message = "Shouldn't be empty")
    @NumberFormat
    @Min(value = 25,message = "Should be greater than 25 years old.")
    private int age;

    //▪ Position: 
        //- Cannot be null.
        //- Must be either "supervisor" or "coordinator" only.
    @NotNull(message = "Shouldn't be empty")
    @Pattern(regexp = "^(supervisor|coordinator)$",message = "Only two position either supervisor or coordinator")
    private String position;

    //▪ onLeave: 
        //- Must be initially set to false.
    @AssertFalse
    private boolean onLeave;

    //▪ hireDate: 
        //- Cannot be null. 
        //- should be a date in the present or the past.
    @NotNull(message = "Shouldn't be empty")
    @PastOrPresent
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate hireDate;

    //▪ AnnualLeave: 
        //- Cannot be null. 
        //- Must be a positive number
    @NotNull(message = "Shouldn't be empty")
    @PositiveOrZero(message = "cannot be negative")
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
