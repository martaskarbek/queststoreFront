import com.codecool.queststore.handlers.LoginHandler;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.services.ServiceFactory;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;

public class LoginTests {

    @Test
    @DisplayName("Checking is login page loading correctly")
    void loginPageCheck() throws IOException {
        //Arrange
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestMethod()).thenReturn("GET");
        CookieHelper cookieHelper = Mockito.mock(CookieHelper.class);
        Mockito.when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        HttpHelper helper = Mockito.mock(HttpHelper.class);
        Mockito.doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        Helpers helpers = new Helpers(helper, cookieHelper, null);
        LoginHandler handler = new LoginHandler(null, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        Mockito.verify(exchange).getRequestMethod();
    }

}
