package com.codecool.queststore.services;

import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.QuestDAO;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;

import java.util.List;

public class QuestService {

    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    QuestDAO questDAO = new QuestDAO(postgreSQLJDBC);

    public Quest getQuest(int id){

        return null;

    }


    public List<Quest> getQuests() throws Exception {
        return  questDAO.getAll();
    }


    public void addQuestToDB(Quest quest) {
        questDAO.add(quest);
    }
}
