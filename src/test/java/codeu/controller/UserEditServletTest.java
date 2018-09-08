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

public class UserEditServletTest {

  private UserEditServlet userEditServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    userEditServlet = new UserEditServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/useredit.jsp"))
            .thenReturn(mockRequestDispatcher);

    mockUserStore = Mockito.mock(UserStore.class);
    userEditServlet.setUserStore(mockUserStore);
  }
  
    @Test
  public void testDoGet() throws IOException, ServletException {
    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("newusername");
    UUID randomUUID = UUID.randomUUID();
    User user = new User(randomUUID, "newusername", "password", Instant.now());
    Mockito.when(mockUserStore.getUser("newusername")).thenReturn(user);
    userEditServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPostUpdatesUserCorrectly() throws IOException, ServletException {

    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("newusername");
    Mockito.when(mockRequest.getParameter("pictureurl")).thenReturn("www.url.com");
    Mockito.when(mockRequest.getParameter("first")).thenReturn("firstname");
    Mockito.when(mockRequest.getParameter("last")).thenReturn("lastname");
    Mockito.when(mockRequest.getParameter("bio")).thenReturn("Example text about a bio.");
    Mockito.when(mockRequest.getParameter("age")).thenReturn("20");
    Mockito.when(mockRequest.getParameter("email")).thenReturn("example@xyz.com");
    Mockito.when(mockRequest.getParameter("phone")).thenReturn("3335551111");


    UserStore mockUserStore = Mockito.mock(UserStore.class);
    userEditServlet.setUserStore(mockUserStore);
    UUID randomUUID = UUID.randomUUID();
    User user = new User(randomUUID, "newusername", "password", Instant.now());
    Mockito.when(mockUserStore.getUser("newusername")).thenReturn(user);

    userEditServlet.doPost(mockRequest, mockResponse);

    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    Mockito.verify(mockUserStore).updateUser(userArgumentCaptor.capture());

    Assert.assertEquals(userArgumentCaptor.getValue().getName(), "newusername");
    Assert.assertEquals(userArgumentCaptor.getValue().getPictureURL(), "www.url.com");
    Assert.assertEquals(userArgumentCaptor.getValue().getBio(), "Example text about a bio.");
    Assert.assertEquals(userArgumentCaptor.getValue().getFirstName(), "firstname");
    Assert.assertEquals(userArgumentCaptor.getValue().getLastName(), "lastname");
    Assert.assertTrue(userArgumentCaptor.getValue().getAge() == 20);
    Assert.assertEquals(userArgumentCaptor.getValue().getEmail(), "example@xyz.com");
    Assert.assertEquals(userArgumentCaptor.getValue().getPhoneNum(), "3335551111");

    Mockito.verify(mockResponse).sendRedirect("/user");
  }


  @Test
  public void testDoPostInvalidUser() throws IOException, ServletException {

    Mockito.when(mockRequest.getParameter("user")).thenReturn("wrongusername");
    Mockito.when(mockRequest.getParameter("pictureurl")).thenReturn("www.url.com");
    Mockito.when(mockRequest.getParameter("first")).thenReturn("firstname");
    Mockito.when(mockRequest.getParameter("last")).thenReturn("lastname");
    Mockito.when(mockRequest.getParameter("bio")).thenReturn("Example text about a bio.");
    Mockito.when(mockRequest.getParameter("age")).thenReturn("20");
    Mockito.when(mockRequest.getParameter("email")).thenReturn("example@xyz.com");
    Mockito.when(mockRequest.getParameter("phone")).thenReturn("3335551111");


    UserStore mockUserStore = Mockito.mock(UserStore.class);
    userEditServlet.setUserStore(mockUserStore);
    UUID randomUUID = UUID.randomUUID();
    User user = new User(randomUUID, "newusername", "password", Instant.now());
    Mockito.when(mockUserStore.getUser("newusername")).thenReturn(user);

    userEditServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "User not logged in.");
    Mockito.verify(mockResponse).sendRedirect("/login");
  }

}