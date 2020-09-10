package com.codecool.queststore.services;

import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.StudentDAO;
import com.codecool.queststore.models.users.Student;

import java.util.List;

public class StudentService {

    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    StudentDAO studentDAO = new StudentDAO(postgreSQLJDBC);


    public void addStudentToDB(Student student) {
        studentDAO.add(student);
    }


    public List<Student> getStudents() throws Exception {
        return studentDAO.getAll();
    }

    public Student getStudent(int id) {
        return studentDAO.get(id);
    }
}
