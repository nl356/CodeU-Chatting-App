<nav>
  <a id="navTitle" href="/">Google TNT</a>
  <a href="/conversations">Conversations</a>
  <% if(request.getSession().getAttribute("user") != null){ %>
    <a href="/user">Hello <%= request.getSession().getAttribute("user") %>!</a>
  <% } else{ %>
    <a href="/login">Login</a>
    <a href="/register">Register</a>
  <% } %>
  <a href="/about.jsp">About</a>
  <% String usr = (String) request.getSession().getAttribute("user");
     if(usr != null){
         boolean check = (boolean) request.getSession().getAttribute("isSuperUser");
         if(check){ %>
         <a href="/testdata">Load Test Data</a>
      <% } %>
  <% } %>

</nav>

