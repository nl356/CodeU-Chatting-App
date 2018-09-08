package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.DefaultDataStore;
import codeu.model.store.basic.UserStore;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Servlet class responsible for user registration.
 */
public class RegisterServlet extends HttpServlet {

  /**
   * Store class that gives access to Users.
   */
  private UserStore userStore;
  
  /**
   * Initialize Super User Whitelist
   */
  private static final ArrayList<String> SUPER_USER_WHITE_LIST = new ArrayList<String>(){{
    add("Jad");
    add("Trisha");
    add("Nathalia");
    add("Tyler");
    add("Han");
  }};

  /**
   * Set up state for handling registration-related requests. This method is only
   * called when running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common
   * setup method for use by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

    if (!username.matches("[\\w*\\s*]*")) {
      request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }

    if (userStore.isUserRegistered(username)) {
      request.setAttribute("error", "That username is already taken.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }
    
    
    if (SUPER_USER_WHITE_LIST.contains(username)) {
      User user = new User(UUID.randomUUID(), username, passwordHash, Instant.now());
      User.Builder userBuilder = new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime());
      userBuilder.setSuperUser(true);
      userBuilder.setBio(DefaultDataStore.DEFAULT_BIO);
      userBuilder.setPictureURL(DefaultDataStore.DEFAULT_PICTURE);
      user = userBuilder.createUser();
      userStore.addUser(user); 
    }
    else {
      User.Builder userBuilder = new User.Builder(UUID.randomUUID(), username, passwordHash, Instant.now());
      userBuilder.setBio(DefaultDataStore.DEFAULT_BIO);
      userBuilder.setPictureURL(DefaultDataStore.DEFAULT_PICTURE);
      userStore.addUser(userBuilder.createUser());
    }
    response.sendRedirect("/login");
    
  }
}