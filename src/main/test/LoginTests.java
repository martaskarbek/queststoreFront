import com.codecool.queststore.handlers.LoginHandler;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.HandlerHelper;
import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.Session;
import com.codecool.queststore.models.users.Admin;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.Student;
import com.codecool.queststore.services.ServiceFactory;
import com.codecool.queststore.services.UserService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

public class LoginTests {

    @Mock
    HttpExchange exchange = mock(HttpExchange.class);

    @Mock
    CookieHelper cookieHelper = mock(CookieHelper.class);

    @Mock
    HttpHelper helper = mock(HttpHelper.class);

    @Mock
    ServiceFactory serviceFactory = mock(ServiceFactory.class);

    @Mock
    UserService userService = mock(UserService.class);

    @Mock
    HandlerHelper handlerHelper = mock(HandlerHelper.class);

    @Mock
    Helpers helpers = new Helpers(helper, cookieHelper, handlerHelper);

    @Test
    void loginPageCheck() throws IOException {

        //Arrange
        when(exchange.getRequestMethod()).thenReturn("GET");
        when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        Helpers helpers = new Helpers(helper, cookieHelper, null);
        LoginHandler handler = new LoginHandler(null, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        verify(exchange).getRequestMethod();
    }

    @Test
    void loginPageCheckUserStudent() throws IOException {

        //Arrange
        when(exchange.getRequestMethod()).thenReturn("POST");
        Headers headers = new Headers();
        when(exchange.getResponseHeaders()).thenReturn(headers);
        when(serviceFactory.getUserService()).thenReturn(userService);
        Student student = new Student(1, "Karol", "Nowak", Role.STUDENT, true, "a@wp.pl",
                "password", "salt");
        Session session = new Session("session", student.getId());
        student.setSession(session);
        when(userService.login(null, null)).thenReturn(student);
        when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        LoginHandler handler = new LoginHandler(serviceFactory, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        verify(exchange, times(2)).getResponseHeaders();
        assertEquals(headers.get("Location").get(0), "/student");
    }

    @Test
    void loginPageCheckUserAdmin() throws IOException {

        //Arrange
        when(exchange.getRequestMethod()).thenReturn("POST");
        Headers headers = new Headers();
        when(exchange.getResponseHeaders()).thenReturn(headers);
        when(serviceFactory.getUserService()).thenReturn(userService);
        Admin admin = new Admin(1, "Karol", "Nowak", Role.ADMIN, true, "a@wp.pl",
                "password", "salt");
        Session session = new Session("session", admin.getId());
        admin.setSession(session);
        when(userService.login(null, null)).thenReturn(admin);
        when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        LoginHandler handler = new LoginHandler(serviceFactory, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        verify(exchange, times(2)).getResponseHeaders();
        assertEquals(headers.get("Location").get(0), "/admin");
    }

    @Test
    void loginPageCheckUserMentor() throws IOException {

        //Arrange
        when(exchange.getRequestMethod()).thenReturn("POST");
        Headers headers = new Headers();
        when(exchange.getResponseHeaders()).thenReturn(headers);
        when(serviceFactory.getUserService()).thenReturn(userService);
        Mentor mentor = new Mentor(1, "Karol", "Nowak", Role.MENTOR, true, "a@wp.pl",
                "password", "salt");
        Session session = new Session("session", mentor.getId());
        mentor.setSession(session);
        when(userService.login(null, null)).thenReturn(mentor);
        when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        LoginHandler handler = new LoginHandler(serviceFactory, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        verify(exchange, times(2)).getResponseHeaders();
        assertEquals(headers.get("Location").get(0), "/mentor");
    }

}
