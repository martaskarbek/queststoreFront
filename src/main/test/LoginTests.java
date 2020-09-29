import com.codecool.queststore.handlers.LoginHandler;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.HandlerHelper;
import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.Session;
import com.codecool.queststore.models.users.Student;
import com.codecool.queststore.services.ServiceFactory;
import com.codecool.queststore.services.UserService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;

public class LoginTests {

    @Mock
    HttpExchange exchange = Mockito.mock(HttpExchange.class);

    @Mock
    CookieHelper cookieHelper = Mockito.mock(CookieHelper.class);

    @Mock
    HttpHelper helper = Mockito.mock(HttpHelper.class);

    @Test
    void loginPageCheck() throws IOException {

        //Arrange
        Mockito.when(exchange.getRequestMethod()).thenReturn("GET");
        Mockito.when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        Mockito.doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        Helpers helpers = new Helpers(helper, cookieHelper, null);
        LoginHandler handler = new LoginHandler(null, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        Mockito.verify(exchange).getRequestMethod();
    }

    @Test
    void loginPageCheckUserStudent() throws IOException {

        //Arrange
        Mockito.when(exchange.getRequestMethod()).thenReturn("POST");
        Headers headers = new Headers();
        Mockito.when(exchange.getResponseHeaders()).thenReturn(headers);
        ServiceFactory serviceFactory = Mockito.mock(ServiceFactory.class);
        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(serviceFactory.getUserService()).thenReturn(userService);
        Student student = new Student(1, "Karol", "Nowak", Role.STUDENT, true, "a@wp.pl",
                "password", "salt");
        Session session = new Session("session", student.getId());
        student.setSession(session);
        Mockito.when(userService.login(null, null)).thenReturn(student);
        HandlerHelper handlerHelper = Mockito.mock(HandlerHelper.class);
        Mockito.when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        Mockito.doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        Helpers helpers = new Helpers(helper, cookieHelper, handlerHelper);
        LoginHandler handler = new LoginHandler(serviceFactory, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        Mockito.verify(exchange, Mockito.times(2)).getResponseHeaders();
        Assertions.assertEquals(headers.get("Location").get(0), "/student");
    }

}
