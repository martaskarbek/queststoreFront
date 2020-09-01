package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Reward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RewardDAO implements IRewardDao{

    private  PostgreSQLJDBC postgreSQLJDBC;

    public RewardDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public void add(Reward reward) {

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

        Reward reward = new Reward();
        reward.setId(id);
        reward.setName(name);
        reward.setDescription(description);
        reward.setPrice(price);
        reward.setCategory(Category.valueOf(categoryId));
        reward.setMentorId(mentorId);
        return reward;
    }

//    private int id;
//    private String name;
//    private String description;
//    private int price;
//    private Category category;
//    private int mentorId;

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
}
