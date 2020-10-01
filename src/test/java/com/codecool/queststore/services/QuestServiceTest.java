package com.codecool.queststore.services;

import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.users.Mentor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

class QuestServiceTest {

    @Test
    void checkQuestUpdate() {

        //Arrange
        Map<String, String> questsMap = new HashMap<>();
        Mentor mentor = new Mentor();
        QuestService questService = Mockito.mock(QuestService.class);

        //Act
        questService.updateQuest(questsMap, mentor);

        //Assert
        Mockito.verify(questService).updateQuest(questsMap, mentor);
    }

}