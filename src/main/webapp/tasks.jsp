<%-- 
    Document   : tasks
    Created on : Jul 15, 2016, 11:16:40 AM
    Author     : MZagrabska
--%>

<%@page import="com.mycompany.application.Employee"%>
<%@page import="com.mycompany.application.Task"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.application.SharedMethods"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    SharedMethods sharedMethods = new SharedMethods();
    ArrayList<Task> list = (ArrayList<Task>) request.getAttribute("taskList");
    String executorSelectionId;
    String checkboxId;
    int selectionIndex = 0;
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tasks list</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/task_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/task_scripts.js"></script>
    </head>
    <body>     
        <div class="buttons">         
            <form>
                <input type="button" value="Back" onClick="history.go(-1); return true; ">
            </form>
            <form action="add_task" method="post" onSubmit="popupform(this, 'join')"> 
                <input type="submit" value="Add" name="add">
            </form>
            <form action="edit_task" method="post" onSubmit="popupform(this, 'join')"> 
                <input type="submit" value="Edit" id="edit" name="edit" disabled="disabled" autocomplete="off">
            </form>
            <form action="assign_task" method="post" onSubmit="popupform(this, 'join')"> 
                <input type="submit" title="Assign new employee to choosen task" value="Assign employees" 
                       id="assign" name="assign" disabled="disabled" autocomplete="off" />
                <input type="hidden" id="tasks" value="" name="tasks"  />
            </form>
            <form action="remove_task" method="post" 
                  onclick="if (confirm('Are you sure you want to remove this employee from this task?')) {
                      document.getElementById('idemp').value = document.getElementById('executor_id').value;
                                                form.action='remove_task'; 
                                            } else { 
                                                return false; 
                                            }"> 
                <input type="hidden" id="idtask" value="" name="idtask"  />
                <input type="hidden" id="idemp" value="" name="idemp"  />
                <input type="submit" title="Remove choosen emoployee from choosen task" value="Remove employee" 
                       id="remove" name="remove" disabled="disabled" autocomplete="off" />
            </form>
        </div>
        <div class="table">
        <TABLE BORDER="1" id="example" class="display" cellspacing="0" width="80%" align="left">
            <THEAD>
                <TR>
                    <TH></TH>
                    <TH style="display:none;"></TH> <!-- Task_id -->
                    <TH style="display:none;"></TH> <!-- Project_id -->
                    <TH>Name</TH>
                    <TH>Project</TH>
                    <TH>Work hours</TH>
                    <TH>Start date</TH>
                    <TH>End date</TH>
                    <TH>Executors</TH>
                    <TH>Status</TH>
                    <TH width="8%"></TH>
                </TR>
            </THEAD>
            <TBODY>
                <% for(Task task: list) { 
                    ++selectionIndex;
                    executorSelectionId = "executor" + selectionIndex;
                    checkboxId = "checkbox" + selectionIndex;
                %>
                <TR onclick="getRowIndex(this);">
                    <TD><input type="radio" class="chb" id="<%=checkboxId%>" name="myTextEditBox" 
                               autocomplete="off" onchange="checkbox(this);" >
                                                                   
                    </TD>
                    <TD style="display:none;" id="task_id"><%=task.getTaskid()%></TD>
                    <TD style="display:none;" id="project_id"><%=task.getProjectid()%></TD>
                    <TD id="name"> <%=task.getName().trim()%></TD>
                    <TD id="project_shortname"><%=sharedMethods.checkUndefinded(task.getProjectShortName()).trim()%></TD>
                    <TD id="whours"> <%=sharedMethods.checkUndefinded(task.getWorkHours().toString()).trim()%></TD>
                    <TD id="sdate"> <%=sharedMethods.checkUndefinded(task.getStartDate()).trim()%></TD>
                    <TD id="edate"> <%=sharedMethods.checkUndefinded(task.getEndDate()).trim()%></TD>
                    <TD id="executor">
                        <% if (task.getEmployeeList() == null || task.getEmployeeList().isEmpty()) { %>
                                <select disabled style="width: 100%" id="<%=executorSelectionId%>" class="field">
                                    <option>None</option>
                                </select>
                            <% } else { %>
                                <select multiple style="width: 100%" id="<%=executorSelectionId%>" class="field" 
                                        onchange="disableButtonChange();" onclick="disableButton();" onselect="disableButtonChange();">
                                    <option hidden></option>
                                        <% for(Employee employee : task.getEmployeeList()) { %>                               
                                                <option value="<%=employee.getEmployeeid()%>"> <%=employee.getLastName()%>                           
                                        <% }
                            } %>    
                        </select>
                    </TD>
                    <TD id="status"> <%=sharedMethods.checkUndefinded(task.getStatus()).trim()%></TD>
                    <TD> 
                        <form action="delete_task" method="post"> 
                            <input type="submit" value="delete" name="delete" style="width:100%" 
                                   onclick=" if (confirm('Are you sure you want to delete?')) { 
                                                form.action='delete_task'; 
                                            } else { 
                                                return false; 
                                            }">
                            <input type="hidden" id="idproject" value="<%=task.getProjectid()%>" name="delete"  />
                        </form>
                    </TD>
                </TR>
                <% } %>
            </TBODY>
        </TABLE>
        <input type="hidden" id="executor_id"  />
        <input type="hidden" id="hiddenRowIndex"  />
        <input type="hidden" id="oldIndex"  />
        <input type="hidden" id="checkbox_index"  />
        </div>
    </body>
</html>

