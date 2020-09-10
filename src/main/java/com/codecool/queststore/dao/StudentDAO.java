package com.codecool.queststore.dao;

import com.codecool.queststore.models.users.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StudentDAO implements IStudentDAO {


    private PostgreSQLJDBC postgreSQLJDBC;

    public StudentDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }


    @Override
    public void add(Student student) {

        postgreSQLJDBC.connect();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("INSERT INTO students" +
                    "(user_id, module_id, wallet) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setInt(2, student.getModuleId());
            preparedStatement.setInt(3, student.getWallet());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void edit(Student student) {

    }

    @Override
    public void remove(Student student) {

    }

    @Override
    public List<Student> getAll() throws Exception {
        return null;
    }

    @Override
    public Student get(int id) {
        return null;
    }
}