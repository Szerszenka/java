<%-- 
    Document   : add_task
    Created on : Jul 15, 2016, 11:16:58 AM
    Author     : MZagrabska
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.application.Project"%>
<%@page import="com.mycompany.application.SharedMethods"%>

<% 
    SharedMethods sharedMethods = new SharedMethods();
    ArrayList<Project> list = (ArrayList<Project>) request.getAttribute("projectList");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add employee</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/task_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/task_scripts.js"></script>
    </head>
    <body class="popup">
        <legend>Task add form </legend>
        <p align="center">Add new task</p>
        <form method="post" class="form"  action="tasks?list=tasks&action=add" name="addTaskForm">
            <fieldset>
                <div class="elements">
                    <label for="name">Name<em>*</em> :</label>
                    <input type="text" id="name" name="name" size="25" class="field" 
                           placeholder="Name" 
                           pattern="^[^\\/?%*:|<>\.]+$" 
                           title="Name should contain at least 2 letters. e.g. Market's Planner">
                </div>
                <div class="elements">
                    <label for="project_id">Project<em>*</em> :</label>
                    <select id="pro_id" class="field" onchange="javascript:setProjectId(this);" 
                            placeholder="Project">
                        <option hidden></option>
                        <% for(Project project : list ){ %>
                            <option value="<%=project.getProjectid()%>"> <%=project.getShortName()%>
                        <% } %>    
                    </select>
                </div>
                <div class="elements"> 
                    <label for="work_hours">Work hours : </label>
                    <input type="text" id="work_hours" name="work_hours" size="25" class="field" 
                           placeholder="Work hours" 
                           pattern="^(\s*|\d+)$"
                           title="OPTIONAL. Work hours should contain only numbers. e.g. 108">
                </div>
                <div class="elements">
                    <label for="start_date">Start date : </label>
                    <input type="text" id="start_date" name="start_date" size="25" class="field" 
                           placeholder="Start date" 
                           pattern="\d{4}-\d{1,2}-\d{1,2}"
                           title="OPTIONAL. Date should be in format yyyy-mm-dd. e.g 2016-02-01">
                </div>
                <div class="elements">
                    <label for="end_date">End date :</label>
                    <input type="text" id="end_date" name="end_date" size="25" class="field" 
                           placeholder="End date" 
                           pattern="\d{4}-\d{1,2}-\d{1,2}" 
                           title="OPTIONAL. Date should be in format yyyy-mm-dd. e.g 2016-02-01">
                </div>
                <div class="elements">
                    <label for="status_list">Status :</label>
                    <select id="status_list" class="field" onchange="javascript:setStatus(this);" 
                            placeholder="Status">
                        <option hidden></option>
                        <option value="Not started" selected="selected">Not started</option>
                        <option value="In progress">In progress</option>
                        <option value="Done">Done</option>
                        <option value="Postpone">Postpone</option>
                    </select>
                </div>
                    <input type="hidden" id="project_id" name="project_id">
                    <input type="hidden" id="status" name="status">
                    <input type="hidden" id="executor_id" name="executor_id">
                <div class="submit">
                    <input type="submit" id="add" value="Add_task" 
                           title="Fill the required fields marked with an asterisk">
                </div>
            </fieldset>
        </form>
    <script>
            var buttonSubmit = document.getElementById('add');
            buttonSubmit.disabled = true;       
            document.getElementById('status').value = "Not started";
    </script>
    </body>
</html>
