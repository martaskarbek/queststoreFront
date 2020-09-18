package com.codecool.queststore.models;

public class StudentQuest {

    int id;
    int studentId;
    int questId;
    QuestStatus questStatus;
    String questSubmission;
    String author;
    String questName;
    int value;

    public StudentQuest(int id, int studentId, int questId, QuestStatus questStatus, String questSubmission) {
        this.id = id;
        this.studentId = studentId;
        this.questId = questId;
        this.questStatus = questStatus;
        this.questSubmission = questSubmission;
    }

    public StudentQuest(){};

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public QuestStatus getQuestStatus() {
        return questStatus;
    }

    public void setQuestStatus(QuestStatus questStatus) {
        this.questStatus = questStatus;
    }

    public String getQuestSubmission() {
        return questSubmission;
    }

    public void setQuestSubmission(String questSubmission) {
        this.questSubmission = questSubmission;
    }

    @Override
    public String toString() {
        return "StudentQuest{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", questId=" + questId +
                ", questStatus=" + questStatus +
                ", questSubmission='" + questSubmission + '\'' +
                ", author='" + author + '\'' +
                ", questName='" + questName + '\'' +
                ", value=" + value +
                '}';
    }
}
