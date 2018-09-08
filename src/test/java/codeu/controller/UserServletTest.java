package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;

public class UserServletTest {

  private UserServlet userServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    userServlet = new UserServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/user.jsp"))
            .thenReturn(mockRequestDispatcher);

    mockUserStore = Mockito.mock(UserStore.class);
    userServlet.setUserStore(mockUserStore);
  }
  
    @Test
  public void testDoGet() throws IOException, ServletException {
    
    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("username");
    UUID fakeUserID = UUID.randomUUID(); 
    
    User fakeUser = new User(fakeUserID, "username", "password", Instant.now());
    Mockito.when(mockUserStore.getUser("username")).thenReturn(fakeUser);
    
    userServlet.doGet(mockRequest, mockResponse);
    
    Mockito.verify(mockRequest).setAttribute("user", fakeUser);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

}