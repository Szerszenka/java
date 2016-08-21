<%-- 
    Document   : edit_task
    Created on : Jul 20, 2016, 11:29:19 AM
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
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Edit employee</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/task_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/task_scripts.js"></script>  
    </head>
    <body class="popup">
        <legend>Task edit form </legend>
        <p align="center">Edit task</p>
        <form method="post" class="form" action="tasks?list=tasks&action=edit" name="editTaskForm">
            <fieldset>
                <div class="elements">
                    <label for="name">Name<em>*</em> :</label>
                    <input type="text" id="name" name="name" size="25" class="field" 
                           placeholder="Name" 
                           pattern="^[^\\/?%*:|<>\.]+$" 
                           title="Name should contain at least 2 letters. e.g. Market's Planner">
                </div>
                <div class="elements">
                    <label>Project<em>*</em> :</label>
                    <select id="pro_id" class="field" onchange="javascript:setProjectId(this); ">
                        <% for(Project project: list){ %>
                        <option value="<%=project.getProjectid()%>"><%=project.getShortName()%></option>
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
                    <select id="status_list" name="status_list" class="field" onchange="javascript:setStatus(this);"
                            placeholder="Status">
                        <option value="Not started">Not started</option>
                        <option value="In progress">In progress</option>
                        <option value="Done">Done</option>
                        <option value="Postpone">Postpone</option>
                    </select>
                </div>
                    <input type="hidden" id="task_id" name="task_id">
                    <input type="hidden" id="project_id" name="project_id">
                    <input type="hidden" id="status" name="status">
                <div class="submit">
                    <input type="submit" id="add" value="Edit_task">
                </div>
            </fieldset>
        </form>
        <script type="text/javascript">
            var buttonSubmit = document.getElementById('add');
            buttonSubmit.disabled = false;
            var rowIndex = window.opener.document.getElementById('hiddenRowIndex').value;
            document.getElementById('task_id').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[1].textContent;
            document.getElementById('project_id').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[2].textContent;
            document.getElementById('name').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[3].textContent.trim();
            document.getElementById('status_list').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[9].textContent.trim();
            document.getElementById('status').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[9].textContent.trim();
            var work = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[5].textContent.trim();
            if(work == "(undefined)") { 
                document.getElementById('work_hours').value = "";
            } else {
                document.getElementById('work_hours').value = work;
            }
            var sdate = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[6].textContent.trim();
            if(sdate == "(undefined)") { 
                document.getElementById('start_date').value = "";
            } else {
                document.getElementById('start_date').value = sdate;
            }
            var edate = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[7].textContent.trim();
            if(edate == "(undefined)") { 
                document.getElementById('end_date').value = "";
            } else {
                document.getElementById('end_date').value = edate;
            }

            var projectId = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[2].textContent;
            var projectSelection = document.getElementById('pro_id');
            for (var i = 0; i < projectSelection.options.length; i++) {
                if (projectSelection.options[i].value.trim() === projectId.trim()) {
                    projectSelection.selectedIndex = i;
                    break;
                }
            }
            
            var statusValue = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[9].textContent;         
            var statusSelection = document.getElementById('status_list');
            for (var i = 0; i < statusSelection.options.length; i++) {
                
                if (statusSelection.options[i].value.trim() === statusValue.trim()) {
                    statusSelection.selectedIndex = i;
                    break;
                }
            }
            
        </script>
    </body>
</html>
