package com.codecool.queststore.dao;

import com.codecool.queststore.models.StudentQuest;

public interface IStudentQuestDAO extends IDAO<StudentQuest>{
    StudentQuest getStudentQuest(int studentId, int questId);
}
