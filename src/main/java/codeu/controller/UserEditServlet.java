package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.store.basic.UserStore;
import codeu.model.data.User;

public class UserEditServlet extends HttpServlet {

  /**
   * Store class that gives access to Users.
   */
  private UserStore userStore;

  /**
   * Set up state for handling about page-related requests. This method is only
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

    /**
   * This function fires when a user requests the /users URL. It simply forwards
   * the request to users.jsp.
   */
    @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them go to profile edit page
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them go to profile edit page
      response.sendRedirect("/login");
      return;
    }

    request.setAttribute("user", user);
    request.getRequestDispatcher("/WEB-INF/view/useredit.jsp").forward(request, response);
  }

  /**
   * This function handles post requests to update the User with all information sent. It creates a
   * user and updates the userstore with the new user information.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String username = ""+request.getSession().getAttribute("user");
    String pictureURL = ""+request.getParameter("pictureurl");
    String first = ""+request.getParameter("first");
    String last = ""+request.getParameter("last");
    String bio = ""+request.getParameter("bio");

    String ageString = ""+request.getParameter("age");
    Integer age = 0;
    if(ageString.length() > 0){
      age = Integer.parseInt(request.getParameter("age"));
    }

    String email = ""+request.getParameter("email");
    String phoneNum = ""+request.getParameter("phone");
    User user = userStore.getUser(username);

    if (user == null) {
      request.setAttribute("error", "User not logged in.");
      response.sendRedirect("/login");
      return;
    }

    User.Builder userBuilder = new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime());
    userBuilder.setAge(age);
    userBuilder.setFirstName(first);
    userBuilder.setLastName(last);
    userBuilder.setEmail(email);
    userBuilder.setPhoneNum(phoneNum);
    userBuilder.setBio(bio);
    userBuilder.setPictureURL(pictureURL);
    userBuilder.setRateLimit(user.getRateLimit());
    user = userBuilder.createUser();

    userStore.updateUser(user);
    request.setAttribute("user", user);
    response.sendRedirect("/user");
  }
}