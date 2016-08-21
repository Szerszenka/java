<%-- 
    Document   : add_project
    Created on : Jul 15, 2016, 11:03:32 AM
    Author     : MZagrabska
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add employee</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/source/styles/project_style.css" />
        <script src="${pageContext.request.contextPath}/source/scripts/project_scripts.js"></script> 
    </head>
    <body class="popup">
        <legend>Project add form </legend>
        <p align="center">Add new project</p>
        <form method="post" class="form"  action="projects?list=projects&action=add" name="addProjectForm">
            <fieldset>
                <div class="elements">
                    <label for="name">Name<em>*</em> :</label>
                    <input type="text" id="name" name="name" size="25" class="field" 
                           placeholder="Name" 
                           pattern="^[^\\/?%*:|<>\.\"\']+$" 
                           title="Name should contain at least 2 letters except .\/?%*:|<>\"\'. e.g. Making website for Qulix">
                </div>
                <div class="elements"> 
                    <label for="short_name">Short Name<em>*</em> :</label>
                    <input type="text" id="short_name" name="short_name" size="25" class="field"
                           placeholder="Enter a short name" 
                           pattern="[A-Za-z0-9]{1,}" 
                           title="Short name should contain at least 1 letter or number. e.g. WQ1">
                </div>
                <div class="elements">
                    <label for="description">Description :</label>
                    <textarea type="text" name="description" size="25" class="field" 
                              rows="8" cols="50"
                              placeholder="Description" 
                              title="OPTIONAL. e.g. Using servlet, postgreSQL, simple table"></textarea>
                </div>
                <div class="submit">
                    <input type="submit" id="add" value="Add_project" 
                           title="Fill the required fields marked with an asterisk">
                </div>
            </fieldset>
        </form>
        <script>
            var buttonSubmit = document.getElementById('add');
            buttonSubmit.disabled = true;
        </script>
    </body>
</html>
