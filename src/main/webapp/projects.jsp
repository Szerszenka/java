<%-- 
    Document   : projects
    Created on : Jul 15, 2016, 11:02:58 AM
    Author     : MZagrabska
--%>

<%@page import="com.mycompany.application.Task"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.application.Project"%>
<%@page import="com.mycompany.application.SharedMethods"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    SharedMethods sharedMethods = new SharedMethods();
    ArrayList<Project> list = (ArrayList<Project>) request.getAttribute("projectList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projects list</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/project_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/project_scripts.js"></script>
    </head>
    <body>     
        <div class="buttons">         
            <form>
                <input type="button" value="Back" onClick="history.go(-1); return true; ">
            </form>
            <form action="add_project" method="post" onSubmit="popupform(this, 'join')"> 
                <input type="submit" value="Add" name="add">
            </form>
            <form action="edit_project" method="post" onSubmit="popupform(this, 'join')"> 
                <input type="submit" value="Edit" id="edit" name="edit" disabled="disabled" autocomplete="off">
            </form>
        </div>
        <div class="table">
        <TABLE BORDER="1" id="example" class="display" cellspacing="0" width="80%" align="left">
            <THEAD>
                <TR>
                    <TH></TH>
                    <TH style="display:none;"></TH>
                    <TH>Name</TH>
                    <TH>Short</TH>
                    <TH>Description</TH>
                    <TH>Task's list</TH>   
                    <TH width="8%"></TH>
                </TR>
            </THEAD>
            <TBODY>
                <% for(Project project : list){ %>
                <TR onclick="getRowIndex(this);">
                    <TD><input type="radio" class="chb" id="checkbox" name="myTextEditBox" 

                               autocomplete="off" onclick="enable_submit(this.checked)"
                    </TD>
                    <TD style="display:none;"><%=project.getProjectid()%></TD>
                    <TD id="name"> <%=project.getName()%></TD>
                    <TD id="sname"> <%=sharedMethods.checkUndefinded(project.getShortName()).trim()%></TD>
                    <TD class="description" id="description"><%= sharedMethods.checkUndefinded(project.getDescription())%></TD>
                     <TD>
                         <%  if (project.getTaskList() != null && !(project.getTaskList().isEmpty())) { %>
                            <TABLE BORDER="1" cellspacing="0">
                                <TR>
                                    <TH>№</TH>
                                    <TH>Name</TH>
                                    <TH>Work hours</TH>
                                    <TH>Start date</TH>
                                    <TH>End date</TH>
                                    <TH>Status</TH>
                                </TR>
                                <% int ordinalNumber = 1;
                                   for(Task task : project.getTaskList()) { %>
                                <TR>
                                    <TD id="ordinal"> <%=ordinalNumber++%></TD>
                                    <TD id="name"> <%=task.getName()%></TD>
                                    <TD id="whours"> <%=sharedMethods.checkUndefinded(task.getWorkHours().toString()).trim()%></TD>
                                    <TD id="sdate"> <%=sharedMethods.checkUndefinded(task.getStartDate()).trim()%></TD>
                                    <TD id="edate"> <%=sharedMethods.checkUndefinded(task.getEndDate()).trim()%></TD>
                                    <TD id="status"> <%=sharedMethods.checkUndefinded(task.getStatus()).trim()%></TD>
                                </TR>
                                <% } %>
                            </TABLE>
                        <% } %>
                        </TD>                    
                    <TD> 
                        <form action="delete_project" method="post"> 
                            <input type="submit" value="delete" name="delete" 
                                   onclick=" if (confirm('You have chosen to delete this project and all tasks related to it, do you wish to process?')) { 
                                                form.action='delete_project'; 
                                            } else { 
                                                return false; 
                                            }">
                            <input type="hidden" id="idproject" value="<%=project.getProjectid()%>" name="delete"  />
                        </form>
                    </TD>
                </TR>
                <% } %>
            </TBODY>
        </TABLE>
            <!-- Hidden field with value of row's index that was clicked -->
        <input type="hidden" id="hiddenRowIndex"  />
        </div>
    </body>
</html>