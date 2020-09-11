package com.codecool.queststore.models.users;


import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.Role;

import java.util.List;

public class Student extends User{

    int studentId;
    int moduleId;
    int wallet;
    String moduleName;
    List<Quest> questList;
    List<Reward> rewardList;

    public Student(int id, String firstName, String lastName, Role role, boolean isActive, String email, String password) {
        super(id, firstName, lastName, role, isActive, email, password);
    }

    public Student() {

    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public List<Reward> getRewardList() {
        return rewardList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", moduleId=" + moduleId +
                ", wallet=" + wallet +
                ", moduleName='" + moduleName + '\'' +
                ", questList=" + questList +
                ", rewardList=" + rewardList +
                '}';
    }

    public void setRewardList(List<Reward> rewardList) {
        this.rewardList = rewardList;
    }


}