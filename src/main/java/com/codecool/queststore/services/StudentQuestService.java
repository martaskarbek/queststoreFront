package com.codecool.queststore.services;

import com.codecool.queststore.dao.StudentDAO;
import com.codecool.queststore.dao.StudentQuestDAO;
import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.QuestStatus;
import com.codecool.queststore.models.StudentQuest;
import com.codecool.queststore.models.users.Student;

import java.util.Map;

public class StudentQuestService {

    private StudentQuestDAO studentQuestDAO;
    private StudentDAO studentDAO;

    public StudentQuestService(StudentQuestDAO studentQuestDAO, StudentDAO studentDAO) {
        this.studentQuestDAO = studentQuestDAO;
        this.studentDAO = studentDAO;
    }

    public StudentQuest getStudentQuest(int studentId, int questId) {
        return studentQuestDAO.getStudentQuest(studentId, questId);
    }

    public void updateStudentQuest(Map<String, String> formData) {
        System.out.println(formData);
        StudentQuest studentQuest = new StudentQuest();
        studentQuest.setStudentId(Integer.parseInt(formData.get("studentId")));
        studentQuest.setQuestId(Integer.parseInt(formData.get("questId")));
        studentQuest.setQuestStatus(QuestStatus.valueOf(Integer.parseInt(formData.get("status"))));
        studentQuest.setQuestSubmission(formData.get("submission"));
        studentQuest.setValue(Integer.parseInt(formData.get("value")));
        update(studentQuest);
        if (studentQuest.getValue() != 0){
            updateStudentWallet(studentQuest);
        }




    }

    private void updateStudentWallet(StudentQuest studentQuest) {
        if (studentQuest.getQuestStatus().equals(QuestStatus.APPROVED)){
            studentDAO.editStudentWallet(studentQuest);
        }
    }

    private void update(StudentQuest studentQuest) {
        studentQuestDAO.edit(studentQuest);

    }
}
