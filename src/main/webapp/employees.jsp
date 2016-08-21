<%-- 
    Document   : employees
    Created on : Jul 15, 2016, 11:16:32 AM
    Author     : MZagrabska
--%>

<%@page import="com.mycompany.application.Employee"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.application.SharedMethods"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    SharedMethods sharedMethods = new SharedMethods();
    ArrayList<Employee> list = (ArrayList<Employee>) request.getAttribute("employeeList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employees list</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/employee_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/employee_scripts.js"></script> 
    </head>
    <body class="main_table">     
        <div class="buttons">
            <form>
                <input type="button" value="Back" onClick="history.go(-1); return true; " >
            </form>
            <form action="add_employee" method="post" onSubmit="popupform(this, 'join')"> 
                <input type="submit" value="add" name="add">
            </form>
            <form action="edit_employee" method="post" onSubmit="popupform(this, 'join')"> 
                <input type="submit" value="edit" id="edit" name="edit" disabled="disabled" autocomplete="off">
            </form>
        </div>
        <div class="table">
        <TABLE BORDER="1" id="example" class="display" cellspacing="0" width="80%" align="left">
            <THEAD>
                <TR>
                    <TH></TH>
                    <TH style="display:none"></TH>
                    <TH>First Name</TH>
                    <TH>Last Name</TH>
                    <TH>Position</TH>
                    <TH width="8%"></TH>
                </TR>
            </THEAD>
            <TBODY>
                <% for(Employee employee : list){ %>
                <TR onclick="getRowIndex(this);">
                    <TD><input type="radio" class="chb" id="checkbox" name="myTextEditBox" 

                               autocomplete="off" onclick="enable_submit(this.checked)"
                    </TD>
                    <TD style="display:none"><%=employee.getEmployeeid()%></TD>
                    <TD id="firstname"> <%=sharedMethods.checkUndefinded(employee.getFirstName()).trim()%></TD>
                    <TD id="lastname"> <%=sharedMethods.checkUndefinded(employee.getLastName()).trim()%></TD>
                    <TD id="position"> <%=sharedMethods.checkUndefinded(employee.getPosition()).trim()%></TD>
                    <TD> 
                        <form action="delete_employee" method="post"> 
                            <input type="submit" value="delete" name="delete" style="width:100%" 
                                   onclick=" if (confirm('Are you sure you want to delete?')) { 
                                                form.action='delete_employee'; 
                                            } else { 
                                                return false; 
                                            }">
                            <input type="hidden" id="idemployee" value="<%=employee.getEmployeeid()%>" name="delete"  />
                        </form>
                    </TD>
                </TR>
                <% } %>
            </TBODY>
        </TABLE>
        <input type="hidden" id="hiddenRowIndex"  />
        </div>
    </body>
</html>
