package com.codecool.queststore.services;

import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.QuestDAO;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.dao.StudentDAO;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.Student;
import com.codecool.queststore.models.users.User;

import java.util.List;
import java.util.Map;

public class StudentService {

    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    StudentDAO studentDAO = new StudentDAO(postgreSQLJDBC);
    RewardDAO rewardDAO = new RewardDAO(postgreSQLJDBC);
    QuestDAO questDAO = new QuestDAO(postgreSQLJDBC);


    public void addStudentToDB(Student student) {
        studentDAO.add(student);
    }


    public List<Student> getStudents() throws Exception {
        return studentDAO.getAll();
    }

    public Student getStudent(int id) {

        Student student = studentDAO.get(id);
        List<Reward> studentRewards = rewardDAO.getStudentRewards(student);
        List<Quest> studentQuests = questDAO.getStudentQuests(student);
        student.setRewardList(studentRewards);
        student.setQuestList(studentQuests);

        return student;
    }

    public void updateStudentByUser(User userStudent, Map<String, String> data) {
        Student student = new Student();
        student.setId(userStudent.getId());
        student.setModuleId(Integer.parseInt(data.get("modules")));
        student.setWallet(Integer.parseInt(data.get("coins")));
        studentDAO.edit(student);
    }
}
