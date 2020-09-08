package com.codecool.queststore.models.users;

//import com.codecool.queststore.model.Quest;
//import com.codecool.queststore.model.Reward;

import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.Role;

import java.util.List;

public class Student extends User{

    int studentId;
    int moduleId;
    int wallet;
    int sharedWalletId;
    List<Quest> questList;
    List<Reward> rewardList;

    public Student(int id, String firstName, String lastName, Role role, boolean isActive, String email, String password) {
        super(id, firstName, lastName, role, isActive, email, password);
    }

    public Student() {

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

    public int getSharedWalletId() {
        return sharedWalletId;
    }

    public void setSharedWalletId(int sharedWalletId) {
        this.sharedWalletId = sharedWalletId;
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

    public void setRewardList(List<Reward> rewardList) {
        this.rewardList = rewardList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", moduleId=" + moduleId +
                ", wallet=" + wallet +
                ", sharedWalletId=" + sharedWalletId +
                ", questList=" + questList +
                ", rewardList=" + rewardList +
                "} " + super.toString();
    }
}