package com.codecool.queststore.services;

import com.codecool.queststore.dao.*;
import com.codecool.queststore.models.Module;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.QuestStatus;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.Student;
import com.codecool.queststore.models.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentService {

    private final StudentDAO studentDAO;
    private final IUserDAO userDAO;
    private final RewardDAO rewardDAO;
    private final QuestDAO questDAO;

    public StudentService(StudentDAO studentDAO, IUserDAO userDAO, RewardDAO rewardDAO, QuestDAO questDAO) {
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.rewardDAO = rewardDAO;
        this.questDAO = questDAO;
    }

    public void addStudent(Student student) {
        studentDAO.add(student);
    }

    public List<Student> getStudents() {
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

    private void updateStudentByUser(User userStudent, Map<String, String> data) {
        Student student = new Student();
        student.setId(userStudent.getId());
        student.setModuleId(Integer.parseInt(data.get("modules")));
        student.setWallet(Integer.parseInt(data.get("coins")));
        studentDAO.edit(student);
    }
    
    private void addUser(User user) {
        userDAO.add(user);
    }
    
    private void updateUserStudent(User userStudent) {
        userDAO.edit(userStudent);
    }

    public void createStudent(Map<String, String> formData) {
        User userStudent = createStudentModel(formData);
        addUser(userStudent);
        Student student = createStudentAccount(formData);
        addStudent(student);
    }

    public void updateStudent(Map<String, String> formData) {
        int userId = Integer.parseInt(formData.get("userId"));
        User userStudent = createStudentModel(formData);
        userStudent.setId(userId);
        updateUserStudent(userStudent);
        updateStudentByUser(userStudent, formData);
    }

    private User createStudentModel(Map<String, String> data) {
        User user = new Student();
        user.setFirstName(data.get("name"));
        user.setLastName(data.get("surname"));
        user.setEmail(data.get("email"));
        user.setPassword(data.get("password"));
        user.setActive(Boolean.parseBoolean(data.get("checkbox")));
        user.setRole(Role.STUDENT);
        return user;
    }

    private Student createStudentAccount(Map<String, String> data) {
        Student student = new Student();
        User studentUser = userDAO.getByCredentials(data.get("email"), data.get("password"));
        student.setId(studentUser.getId());
        student.setModuleId(Integer.parseInt(data.get("modules")));
        student.setWallet(Integer.parseInt(data.get("coins")));
        return student;
    }

    public List<Quest> getSubmittedQuests(Student student) {
        List<Quest> submittedQuests = new ArrayList<>();
        List<Quest> studentQuests = questDAO.getStudentQuests(student);
        for (Quest quest : studentQuests){
            if (quest.getQuestStatus().equals(QuestStatus.SUBMITTED)){
                submittedQuests.add(quest);
            }
        }
        return submittedQuests;
    }

    public List<Student> createStudentListWithQuestsToMark(Mentor mentor, List<Student> students) {
        List<Student> mentorStudents = new ArrayList<>();
        for (Module module : mentor.getModules()) {
            for (Student student : students){
                if (module.getName().equals(student.getModuleName())){
                    List<Quest> studentSubmittedQuests = getSubmittedQuests(student);
                    if(!studentSubmittedQuests.isEmpty()){
                        student.setQuestList(studentSubmittedQuests);
                        mentorStudents.add(student);

                    }
                }
            }
        }
        return mentorStudents;
    }

    public Student getStudentByUser(User user) {
        Student student = studentDAO.get(user.getId());
        List<Quest> studentQuests = questDAO.getStudentQuests(student);
        List<Reward> studentRewards = rewardDAO.getStudentRewards(student);
        student.setQuestList(studentQuests);
        student.setRewardList(studentRewards);
        return student;
    }
}
