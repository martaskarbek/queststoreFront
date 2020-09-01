package com.codecool.queststore.dao;

import com.codecool.queststore.models.Reward;

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

    @Override
    public List<Reward> getAll() {
        return null;
    }

    @Override
    public Reward get(int id) {
        return null;
    }
}
