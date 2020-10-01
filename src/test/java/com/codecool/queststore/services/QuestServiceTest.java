package com.codecool.queststore.services;

import com.codecool.queststore.dao.QuestDAO;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.users.Mentor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class QuestServiceTest {

    QuestService questService = Mockito.mock(QuestService.class);
    Map<String, String> questsMap = Mockito.mock(Map.class);
    Mentor mentor = new Mentor();

    @Test
    void checkQuestUpdate() {

        //Arrange
        //Everything prepared up

        //Act
        questService.updateQuest(questsMap, mentor);

        //Assert
        Mockito.verify(questService).updateQuest(questsMap, mentor);
    }

    @Test
    void checkCreateQuestModel() {
        //Arrange
        //Everything prepared up

        //Act
        questService.createQuest(questsMap, mentor);

        //Assert
        Mockito.verify(questService).createQuest(questsMap, mentor);
    }

    @Test
    void checkUpdateQuest() {
        //Arrange
        Quest quest = new Quest();

        //Act
        questService.updateQuest(quest);

        //Assert
        Mockito.verify(questService).updateQuest(quest);
    }

}