<%-- 
    Document   : index
    Created on : Jul 13, 2016, 12:22:33 PM
    Author     : MZagrabska
--%>
<!-- Main menu with list of lists-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/main.css" />
  </head>

<body>

  <ol>
    <li><a href="#">Main menu</a>
      <ul>
        <li><a href="lists/employees?list=employees">Employees List</a></li>
        <li><a href="lists/projects?list=projects">Projects List</a></li>
        <li><a href="lists/tasks?list=tasks">Task List</a></li>
      </ul>
    </li>
  </ol>
</body>
</html>