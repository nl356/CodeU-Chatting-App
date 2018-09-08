<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<html>
<head>
  <title>CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
  <link rel="stylesheet" type= "text/css" href="/css/about.css">
</head>
<body>
<%@ include file = "WEB-INF/view/navbar.jsp" %>
  <div id = "container">
    <div style = "width:90%; margin-left:auto; margin-right:auto; margin-top: 50px;">

       <h1>What is the CodeU Chat App?</h1>
       <p>
         This Java-based app is aimed at simple and quick communication between teams.
         Built by CodeU'ers, this app is a semester long project that allows us to practice
         our software engineering and team works skills. Here, you can find information on
         recently added features and improvements.
       </p>

       <div class = "category">
         <h2>Recently Added Features</h2>
         <ul class = "list">
           <li>
             <code>Added Home and About page</code>
           </li>
           <li>
             <code>Added Text Cleaning and Text Customization</code>
           </li>
           <li>
             <code>Added Profile Page for users with customization</code>
           </li>
           <li>
             <code>Added Superuser privileges for performing administrative work</code>
           </li>
         </ul>
       </div>

       <div class = "category">
         <h2>Bug fixes/Improvements</h2>
         <ul class = "list">
           <li>
             <code>All tests now passing</code>
           </li>
           <li>
             <code>Enhanced performance when retrieving single users from database</code>
           </li>
           <li>
             <code>Fixed bugs with profile page not allowing you to keep previous information</code>
           </li>
           <li>
             <code>Tightied up Profile page jsp and CSS</code>
           </li>
         </ul>
       </div>

    </div>
  </div>
</body>
</html>
