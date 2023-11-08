package com.think.restDemo.model;

public class StudentAPI {
    private String StudentId;
    private String StudentName;
    private String StudentClass;
    private String StudentScore;

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentClass() {
        return StudentClass;
    }

    public void setStudentClass(String studentClass) {
        StudentClass = studentClass;
    }

    public String getStudentScore() {
        return StudentScore;
    }

    public void setStudentScore(String studentScore) {
        StudentScore = studentScore;
    }



    public StudentAPI(String studentId, String studentName, String studentClass, String studentScore) {
        StudentId = studentId;
        StudentName = studentName;
        StudentClass = studentClass;
        StudentScore = studentScore;
    }


}
