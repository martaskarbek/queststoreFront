package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.OrderStatus;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.Student;
import com.codecool.queststore.models.users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RewardDAO implements IRewardDao{

    private final Connector connector;

    public RewardDAO(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void add(Reward reward) {
        connector.connect();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("INSERT INTO rewards" +
                    "(name, description, price, category_id, mentor_id, isactive) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, reward.getName());
            preparedStatement.setString(2, reward.getDescription());
            preparedStatement.setInt(3, reward.getPrice());
            preparedStatement.setInt(4, Category.getCategoryValue(reward.getCategory()));
            preparedStatement.setInt(5, reward.getMentorId());
            preparedStatement.setBoolean(6, reward.getIsActive());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Reward reward) {
        connector.connect();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("UPDATE rewards SET name=?, description=?, price=?, category_id=?, mentor_id=?, isactive=? WHERE id=?");
            preparedStatement.setString(1, reward.getName());
            preparedStatement.setString(2, reward.getDescription());
            preparedStatement.setInt(3, reward.getPrice());
            preparedStatement.setInt(4, Category.getCategoryValue(reward.getCategory()));
            preparedStatement.setInt(5, reward.getMentorId());
            preparedStatement.setBoolean(6, reward.getIsActive());
            preparedStatement.setInt(7, reward.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        Boolean isActive = rs.getBoolean("isactive");

        Reward reward = new Reward(id, name, description, price, Category.valueOf(categoryId), mentorId, isActive);

        try {
            rs.findColumn("author");
            String author = rs.getString("author");
            reward.setAuthor(author);
            return reward;
        } catch (SQLException sqlex){
            return reward;
        }
    }

    @Override
    public List<Reward> getAll() {
        List<Reward> rewards = new ArrayList<>();

        try {
            connector.connect();
            ResultSet rs = connector.statement.executeQuery("select rewards.id, rewards.name, rewards.description, rewards.price, rewards.category_id, rewards.mentor_id, rewards.isactive, CONCAT(users.first_name, ' ', users.last_name) as author\n" +
                    "from rewards\n" +
                    "inner join mentors on rewards.mentor_id=mentors.id\n" +
                    "inner join users on mentors.user_id=users.id\n" +
                    "group by rewards.id, author;");
            while (rs.next()) {
                Reward reward = create(rs);
                rewards.add(reward);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rewards;
    }

    @Override
    public Reward get(int id) {
        connector.connect();
        Reward reward = new Reward();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("SELECT * FROM rewards WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reward = create(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reward;
    }

    @Override
    public List<Reward> getAllMentorRewards(User user) {
        List<Reward> mentorRewards = new ArrayList<>();

        try {
            connector.connect();
            PreparedStatement preparedStatement = connector.connection.prepareStatement("SELECT rewards.id, rewards.name, rewards.description, rewards.price, rewards.category_id, rewards.mentor_id, rewards.isactive\n" +
                    "FROM rewards, mentors, users\n" +
                    "WHERE mentors.user_id = users.id\n" +
                    "AND mentors.id = rewards.mentor_id AND users.id=?;");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reward reward = create(resultSet);
                mentorRewards.add(reward);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentorRewards;
    }

    public List<Reward> getStudentRewards(Student student) {
        List<Reward> studentRewards = new ArrayList<>();

        try {
            connector.connect();
            PreparedStatement preparedStatement = connector.connection.prepareStatement("select rewards.id, rewards.name, rewards.description, rewards.price, rewards.category_id, rewards.mentor_id, rewards.isactive, orders.order_status_id\n" +
                    "from rewards, orders\n" +
                    "where orders.reward_id=rewards.id\n" +
                    "and orders.student_id=?;");
            preparedStatement.setInt(1, student.getStudentId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reward reward = create(resultSet);
                int orderStatusId = resultSet.getInt("order_status_id");
                OrderStatus orderStatus = OrderStatus.valueOf(orderStatusId);
                reward.setOrderStatus(orderStatus);
                studentRewards.add(reward);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentRewards;
    }
}
