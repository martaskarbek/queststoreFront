package com.codecool.queststore.dao;

import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserPostgreSQLDAOTest {

    //    @Test
//    public void testIsUserAdd() {
//        Connector connector;
//        connector = mock(Connector.class);
//        UserPostgreSQLDAO userDao = mock(UserPostgreSQLDAO.class);
////        UserPostgreSQLDAO userDao = new UserPostgreSQLDAO(connector);
//        User userMock = new Mentor(1, "Ktos", "Cos", Role.MENTOR, true, "mentor@wp.pl",
//                "password", "salt");
//        userDao.add(userMock);
////        when(userDao.add(userMock)).thenReturn(userMock);
//        User user = userDao.get(1);
//        assertAll(
//                () -> assertEquals(userMock.getFirstName(), user.getFirstName()),
//                () -> assertEquals(userMock.getLastName(), user.getLastName()),
//                () -> assertEquals(userMock.getRole(), user.getRole()),
//                () -> assertEquals(userMock.isActive(), user.isActive()),
//                () -> assertEquals(userMock.getEmail(), user.getEmail()),
//                () -> assertEquals(userMock.getPassword(), user.getPassword()),
//                () -> assertEquals(userMock.getSalt(), user.getSalt())
//        );
//    }

    @Test
    public void testIsUserAdd() {
        UserPostgreSQLDAO userDao = mock(UserPostgreSQLDAO.class);
        User user = new Mentor();

        userDao.add(user);

        verify(userDao).add(user);
    }

}