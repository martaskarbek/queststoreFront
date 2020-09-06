package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RewardDAO implements IRewardDao{

    private  PostgreSQLJDBC postgreSQLJDBC;

    public RewardDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public void add(Reward reward) {
        postgreSQLJDBC.connect();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("INSERT INTO rewards" +
                    "(name, description, price, category_id, mentor_id) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, reward.getName());
            preparedStatement.setString(2, reward.getDescription());
            preparedStatement.setInt(3, reward.getPrice());
            preparedStatement.setInt(4, Category.getCategoryValue(reward.getCategory()));
            preparedStatement.setInt(5, reward.getMentorId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void edit(Reward reward, String[] params) {

    }

    @Override
    public void remove(Reward reward) {

    }

    public Reward create(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        int price = rs.getInt("price");
        int categoryId = rs.getInt("category_id");
        int mentorId = rs.getInt("mentor_id");

        return new Reward(id, name, description, price, Category.valueOf(categoryId), mentorId);
    }


    @Override
    public List<Reward> getAll() throws Exception {
        List<Reward> rewards = new ArrayList<>();

        try {
            postgreSQLJDBC.connect();
            ResultSet rs = postgreSQLJDBC.statement.executeQuery("SELECT * FROM rewards;");
            while (rs.next()) {
                Reward reward = create(rs);
                rewards.add(reward);
            }
            rs.close();
            postgreSQLJDBC.statement.close();
            postgreSQLJDBC.connection.close();

            return rewards;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new Exception("Data not found");
    }

    @Override
    public Reward get(int id) {
        return null;
    }

    @Override
    public List<Reward> getAllMentorRewards(User user) throws Exception {
        List<Reward> mentorRewards = new ArrayList<>();

        try {
            postgreSQLJDBC.connect();
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("SELECT rewards.id, rewards.name, rewards.description, rewards.price, rewards.category_id, rewards.mentor_id\n" +
                    "FROM rewards, mentors, users\n" +
                    "WHERE mentors.user_id = users.id\n" +
                    "AND mentors.id = rewards.mentor_id AND users.id=?;");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reward reward = create(resultSet);
                mentorRewards.add(reward);

            }
            resultSet.close();
            postgreSQLJDBC.statement.close();
            postgreSQLJDBC.connection.close();

            return mentorRewards;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new Exception("Data not found");
    }
}
