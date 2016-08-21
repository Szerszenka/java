<%-- 
    Document   : assign_task
    Created on : Jul 26, 2016, 2:48:42 PM
    Author     : MZagrabska
--%>

<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.application.Employee"%>
<%@page import="com.mycompany.application.SharedMethods"%>
<%@page import="com.sun.javafx.scene.web.Debugger"%>

<% 
    SharedMethods sharedMethods = new SharedMethods();
    ArrayList<Employee> list = (ArrayList<Employee>) request.getAttribute("employeeList");
%>  

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assign employees</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/task_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/task_scripts.js"></script>
    </head>
    <body class="popup">
        <legend>Task assign form </legend>
        <p align="center" id="title"></p>
        <form method="post" class="form" action="tasks?list=tasks&action=assign" name="assignTaskForm">
            <fieldset>
                <div class="elements">
                    <p>Employee's list</p>
                    <label for="executor">Last name<em>*</em> :</label>
                    <select multiple name="executors" id="executor" class="field" onchange="javascript:setExecutorId(this);">
                        <option hidden></option>
                        <%  for (Employee employee : list) { %>
                            <option value="<%=employee.getEmployeeid()%>"> <%=employee.getLastName()%>     
                       <% } %>
                    </select>
                </div>
                    <input type="hidden" id="task_id" name="task_id">
                    <input type="hidden" id="executor_id" name="executor_id">
                <div class="submit">
                    <input type="submit" id="add" value="Assign">
                </div>
            </fieldset>
        </form>
    <script>
        window.onload=function(){
            var buttonSubmit = document.getElementById('add');
            buttonSubmit.disabled = false;  
            var rowIndex = window.opener.document.getElementById('hiddenRowIndex').value;
            document.getElementById('task_id').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[1].textContent;
            document.getElementById('title').innerHTML = "Assign new employee to " + window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[3].textContent.trim();
            document.getElementById('executor_id').value = window.opener.document.getElementById('executor_id').value;
        };
    </script> 
    </body>
</html>