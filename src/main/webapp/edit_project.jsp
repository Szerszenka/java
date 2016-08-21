<%-- 
    Document   : edit_project
    Created on : Jul 19, 2016, 2:48:34 PM
    Author     : MZagrabska
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Edit employee</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/project_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/project_scripts.js"></script> 
    </head>
    <body class="popup">
        <legend>Project edit form </legend>
        <p align="center">Edit project</p>
        <form method="post" class="form" action="projects?list=projects&action=edit" name="editProjectForm">
            <fieldset>
                <div class="elements">
                    <label for="first_name">Name<em>*</em> :</label>
                    <input type="text" id="name" name="name" size="25" class="field" 
                           placeholder="Name" 
                           pattern="^[^\\/?%*:|<>\.\"\']+$" 
                           title="Name should contain at least 2 letters except .\/?%*:|<>\"\'. e.g. Making website for Qulix">
                </div>
                <div class="elements"> 
                    <label for="last_name">Short Name<em>*</em> :</label>
                    <input type="text" id="short_name" name="short_name" size="25" class="field" 
                           placeholder="Enter a short name" 
                           pattern="[A-Za-z0-9]{1,}" 
                           title="Short name should contain at least 1 letter or number. e.g. WQ1">
                </div>
                <div class="elements">
                    <label for="position">Description :</label>
                    <textarea id="desc" name="description" size="25" class="field" 
                              rows="8" cols="50"
                              placeholder="Description" 
                              title="OPTIONAL. e.g. Using servlet, postgreSQL, simple table"></textarea>
                </div>
                    <input type="hidden" id="id_pro" name="id_pro" size="25" class="field">
                <div class="submit">
                    <input type="submit" id="add" value="Edit project" >
                </div>
            </fieldset>
        </form>
        <script type="text/javascript">
            var buttonSubmit = document.getElementById('add'); 
            buttonSubmit.disabled = false;
            var rowIndex = window.opener.document.getElementById('hiddenRowIndex').value;
            document.getElementById('id_pro').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[1].textContent;
            document.getElementById('name').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[2].textContent.trim();
            document.getElementById('short_name').value = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[3].textContent.trim();
            var desc = window.opener.document.getElementById('example').rows[parseInt(rowIndex)].cells[4].textContent.trim();     
            if(desc == "(undefined)") { 
                document.getElementById('desc').value = "";
            } else {
                document.getElementById('desc').value = desc;
            }
        </script>
    </body>
</html>
