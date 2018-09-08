package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.store.basic.UserStore;
import codeu.model.data.User;

public class UserServlet extends HttpServlet {

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
      // user is not logged in, don't let them go to profile page
      response.sendRedirect("/login");
      return;
    }

    String optionalUsername = request.getParameter("user");
    boolean isSameUser = true;

    if (optionalUsername != null){
      if(optionalUsername.compareTo(username) != 0){
        isSameUser = false;
      }
      username = optionalUsername;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them go to profile page
      response.sendRedirect("/login");
      return;
    }

    request.setAttribute("user", user);
    request.setAttribute("allowEdit", isSameUser);
    request.getRequestDispatcher("/WEB-INF/view/user.jsp").forward(request, response);

  }
}