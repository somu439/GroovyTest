package com.think.restDemo.controller;

import com.think.restDemo.model.StudentAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentAPIService {

    StudentAPI studentAPI;

    @GetMapping("{StudentId}")
    public StudentAPI getStudentAPI(String studentId){
//        return new StudentAPI( "12345","Sree","Grade10","250");
        return studentAPI;
    }

    @PostMapping()
    public String postStudentAPI(@RequestBody StudentAPI studentAPI){
        this.studentAPI=studentAPI;
        return "Student created successfully";
    }
    @PutMapping()
    public String putStudentAPI(@RequestBody StudentAPI studentAPI){
        this.studentAPI=studentAPI;
        return "Student updated successfully";
    }

    @DeleteMapping("{StudentId}")
    public String putStudentAPI(String studentId){
        this.studentAPI =null;
        return "Student deleted successfully";
    }
}
