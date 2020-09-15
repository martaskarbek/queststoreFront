package com.codecool.queststore.services;

import com.codecool.queststore.dao.StudentQuestDAO;
import com.codecool.queststore.models.StudentQuest;

public class StudentQuestService {

    private StudentQuestDAO studentQuestDAO;

    public StudentQuestService(StudentQuestDAO studentQuestDAO) {
        this.studentQuestDAO = studentQuestDAO;
    }

    public StudentQuest getStudentQuest(int studentId, int questId) {
        return studentQuestDAO.getStudentQuest(studentId, questId);
    }
}
