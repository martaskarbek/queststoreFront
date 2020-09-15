package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.QuestStatus;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.StudentQuest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentQuestDAO implements IStudentQuestDAO {

    private Connector connector;

    public StudentQuestDAO(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void add(StudentQuest studentQuest) {

    }

    @Override
    public void edit(StudentQuest studentQuest) {

    }

    @Override
    public void remove(StudentQuest studentQuest) {

    }

    @Override
    public List<StudentQuest> getAll() throws Exception {
        return null;
    }

    @Override
    public StudentQuest get(int id) {
        return null;
    }

    @Override
    public StudentQuest getStudentQuest(int studentId, int questId) {
        connector.connect();
        StudentQuest studentQuest = new StudentQuest();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("select student_quests.id, student_quests.student_id, student_quests.quest_id, student_quests.quest_status_id, student_quests.quest_input_area, CONCAT(users.first_name, ' ', users.last_name) as author, quests.name as quest_name, quests.coins_to_earn as value\n" +
                    "from student_quests, students, users, quests\n" +
                    "where student_quests.student_id=students.id\n" +
                    "and students.user_id=users.id\n" +
                    "and student_quests.quest_id=quests.id\n" +
                    "and quest_id=? and student_id=?;");
            preparedStatement.setInt(1, questId);
            preparedStatement.setInt(2, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentQuest = create(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentQuest;
    }

    private StudentQuest create(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int studentId = rs.getInt("student_id");
        int questId = rs.getInt("quest_id");
        int questStatusId = rs.getInt("quest_status_id");
        int value = rs.getInt("value");
        String questSubmission = rs.getString("quest_input_area");
        String author = rs.getString("author");
        String questName = rs.getString("quest_name");

        StudentQuest studentQuest = new StudentQuest(id, studentId, questId, QuestStatus.valueOf(questStatusId), questSubmission);

        studentQuest.setAuthor(author);
        studentQuest.setQuestName(questName);
        studentQuest.setValue(value);

        return studentQuest;

    }
}
