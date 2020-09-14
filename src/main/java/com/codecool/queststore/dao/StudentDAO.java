package com.codecool.queststore.dao;

import com.codecool.queststore.models.users.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudentDAO {

    private Connector connector;

    public StudentDAO(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void add(Student student) {

        connector.connect();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("INSERT INTO students" +
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
        connector.connect();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("UPDATE students SET module_id=?, wallet=? WHERE user_id=?");
            preparedStatement.setInt(1, student.getModuleId());
            preparedStatement.setInt(2, student.getWallet());
            preparedStatement.setInt(3, student.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Student student) {

    }

    public Student create(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        Boolean isActive = rs.getBoolean("isactive");
        int studentId = rs.getInt("student_id");
        int wallet = rs.getInt("wallet");
        int moduleId = rs.getInt("module_id");
        String moduleName = rs.getString("module_name");

        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPassword(password);
        student.setActive(isActive);
        student.setStudentId(studentId);
        student.setWallet(wallet);
        student.setModuleId(moduleId);
        student.setModuleName(moduleName);

        return student;

//        try {
//            rs.findColumn("author");
//            String author = rs.getString("author");
//            quest.setAuthor(author);
//            return quest;
//        } catch (SQLException sqlex){
//            return quest;
//        }
    }

    @Override
    public List<Student> getAll() throws Exception {

        List<Student> students = new ArrayList<>();

        try {
            connector.connect();
            ResultSet rs = connector.statement.executeQuery("select users.id as user_id, users.first_name, users.last_name, users.email, users.password, users.isactive, students.id as student_id, students.wallet, students.module_id, modules.name as module_name \n" +
                    "from users\n" +
                    "inner join students on users.id=students.user_id\n" +
                    "inner join modules on students.module_id = modules.id\n" +
                    "group by students.id, users.id, modules.name;");
            while (rs.next()) {
                Student student = create(rs);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student get(int id) {
        connector.connect();
        Student student = new Student();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("select users.id as user_id, users.first_name, users.last_name, users.email, users.password, users.isactive, students.id as student_id, students.wallet, students.module_id, modules.name as module_name \n" +
                    "from users, students, modules\n" +
                    "where users.id=students.user_id\n" +
                    "and students.module_id = modules.id\n" +
                    "and users.id=?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                student = create(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
}