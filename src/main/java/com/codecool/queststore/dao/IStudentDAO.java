package com.codecool.queststore.dao;


import com.codecool.queststore.models.StudentQuest;
import com.codecool.queststore.models.users.Student;

public interface IStudentDAO extends IDAO<Student>{

    void editStudentWallet(StudentQuest studentQuest);
}
