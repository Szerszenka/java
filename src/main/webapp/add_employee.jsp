<%-- 
    Document   : add_employee
    Created on : Jul 15, 2016, 11:16:50 AM
    Author     : MZagrabska
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add employee</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/employee_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/employee_scripts.js"></script> 
    </head>
    <body class="popup">
        <legend>Employee add form </legend>
        <p align="center">Add new employee</p>
        <form method="post" class="form"  action="employees?list=employees&action=add" name="addEmployeeForm">
            <fieldset>
                <div class="elements">
                    <label for="first_name">First Name<em>*</em> :</label>
                    <input type="text" id="first_name" name="first_name" size="25" class="field" 
                           placeholder="Name"
                           pattern="[A-Za-z]{2,}"
                           title="Name should contain at least 2 letters. e.g. John">
                </div>
                <div class="elements"> 
                    <label for="last_name">Last Name<em>*</em> :</label>
                    <input type="text" id="last_name" name="last_name" size="25" class="field"
                           placeholder="Surname" 
                           pattern="[A-Za-z]{2,}"
                           title="Surname should contain at least 2 letters. e.g. Dranny">
                </div>
                <div class="elements">
                    <label for="position">Position :</label>
                    <input type="text" id="position" name="position"  size="25" class="field" 
                           placeholder="Positon" 
                           title="OPTIONAL. e.g. Menager">
                </div>
                <div class="submit">
                    <input type="submit" id="add" value="Add employee" 
                           title="Fill the required fields marked with an asterisk" >
                </div>
            </fieldset>
        </form>
        <script>
            var buttonSubmit = document.getElementById('add'); 
            buttonSubmit.disabled = true;
        </script>
    </body>
</html>
