package com.codecool.queststore.services;

import com.codecool.queststore.dao.QuestDAO;
import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.users.Mentor;

import java.util.List;
import java.util.Map;

public class QuestService {

    QuestDAO questDAO;

    public QuestService(QuestDAO questDAO) {
        this.questDAO = questDAO;
    }

    public Quest getQuest(int id){
        return questDAO.get(id);
    }

    public List<Quest> getQuests() throws Exception {
        return  questDAO.getAll();
    }

    public void addQuest(Quest quest) {
        questDAO.add(quest);
    }

    public void updateQuest(Quest quest) {
        questDAO.edit(quest);
    }

    public void createQuest(Map<String, String> formData, Mentor mentor) {
        Quest quest = createQuestModel(formData, mentor);
        addQuest(quest);
    }

    private Quest createQuestModel(Map<String, String> formData, Mentor mentor) {
        Quest quest = new Quest();
        quest.setName(formData.get("name"));
        quest.setDescription(formData.get("description"));
        quest.setCoinsToEarn(Integer.parseInt(formData.get("price")));
        quest.setModuleId(Integer.parseInt(formData.get("modules")));
        quest.setMentorId(mentor.getMentorId());
        quest.setCategory(Category.valueOf(Integer.parseInt(formData.get("radio"))));
        quest.setActive(Boolean.parseBoolean(formData.get("checkbox")));
        return quest;
    }

    public void updateQuest(Map<String, String> formData, Mentor mentor) {
        int rewardId = Integer.parseInt(formData.get("questId"));
        Quest quest = createQuestModel(formData, mentor);
        quest.setId(rewardId);
        updateQuest(quest);
    }
}
