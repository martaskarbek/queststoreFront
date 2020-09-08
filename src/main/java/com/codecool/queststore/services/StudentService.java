package com.codecool.queststore.services;

import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.StudentDAO;
import com.codecool.queststore.models.users.Student;

public class StudentService {

    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    StudentDAO studentDAO = new StudentDAO(postgreSQLJDBC);


    public void addStudentToDB(Student student) {
        studentDAO.add(student);
    }


}
