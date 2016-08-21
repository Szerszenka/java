<%-- 
    Document   : edit_employee
    Created on : Jul 18, 2016, 10:54:02 AM
    Author     : MZagrabska
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Edit employee</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/employee_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/employee_scripts.js"></script> 
    </head>
    <body class="popup">
        <legend>Employee edit form </legend>
        <p align="center">Edit employee</p>
        <form method="post" class="form" action="employees?list=employees&action=edit" name="editEmployeeForm">
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
                    <input type="text" id="pos" name="position" size="25" class="field"
                           placeholder="Positon" 
                           title="OPTIONAL. e.g. Menager">
                </div>
                    <input type="hidden" id="id_emp" name="id_emp" size="25" class="field">
                <div class="submit">
                    <input type="submit" id="add" value="Edit employee" 
                           title="Fill the required fields marked with an asterisk">
                </div>
            </fieldset>
        </form>
        <script type="text/javascript">
            var buttonSubmit = document.getElementById('add'); 
            buttonSubmit.disabled = false;
            var rowIndex = window.opener.document.getElementById('hiddenRowIndex').value;
            document.getElementById('id_emp').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[1].textContent;
            document.getElementById('first_name').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[2].textContent.trim();
            document.getElementById('last_name').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[3].textContent.trim();
            var pos = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[4].textContent.trim();      
            if(pos == "(undefined)") {
                document.getElementById('pos').value = "";
            } else {
                document.getElementById('pos').value = pos;
            }
        </script>
    </body>
</html>
