/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MZagrabska
 */
public class MainPage extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * It take parameters main menu and redirect to suitable list form
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException{
        String parametrOfRequest = request.getParameter("list");
        SharedMethods sharedMethods = new SharedMethods();
        /*Dane beda znajowaly sie pozniej w osobnym pliku konfiguracyjnym*/
        String connectionURL = "jdbc:postgresql://localhost:5432/postgres";
        String login = "postgres";
        String password = "superuser";
        Connection conn = null;
        try {
            conn = sharedMethods.databaseConnection(connectionURL, login, password);
            /*The three types of lists you can go from index.jsp*/
            Dao dao = new Dao();
            switch (parametrOfRequest) {
                case "employees" : /*Reforward to Employees list*/
                    List<Employee> employeeList = dao.employeeList();
                    request.setAttribute("employeeList", employeeList);
                    getServletConfig().getServletContext().getRequestDispatcher(
                    "/employees.jsp").forward(request,response);
                    break;
                case "projects" : /*Reforward to projects list*/
                    List<Project> projectList = dao.projectList();
                    request.setAttribute("projectList", projectList);
                    getServletConfig().getServletContext().getRequestDispatcher(
                    "/projects.jsp").forward(request,response);       
                    break;
                case "tasks" : /*Reforward to Tasks list*/
                    List<Task> taskList = dao.taskList();
                    request.setAttribute("taskList", taskList);
                    getServletConfig().getServletContext().getRequestDispatcher(
                    "/tasks.jsp").forward(request,response);
                    break;
                default :
                    throw new IllegalArgumentException("Unexpected value " + parametrOfRequest);
            }
        } catch (ServletException | IOException | SQLException | ParseException | ClassNotFoundException e) {
          Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * It take parameters from popup forms add_employee, edit_employee, add_project, edit_project, add_task, edit_task, assign_task
     * It's a main controller working with database (excluding then popup form is not needed)
     * It execute SQL command and redirect with reloading to parent putting alert of successful
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
@   Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String connectionURL = "jdbc:postgresql://localhost:5432/postgres";
        String whatIsAdded = request.getParameter("list");
        String theAction = request.getParameter("action");
        SharedMethods sharedMethods = new SharedMethods();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>asxdfgfds</title>");
            out.println("</head>");
            out.println("<body>");
            Class.forName("org.postgresql.Driver");
           
            Connection conn = DriverManager.getConnection(connectionURL, "postgres", "superuser");
            PreparedStatement preparedStatement = null;
            StringBuilder sqlStatement = new StringBuilder();
            switch(whatIsAdded) {
                case "projects" :
                    if("add".equals(theAction)) {
                        sqlStatement.append("INSERT INTO \"Project\" (\"Name\", \"ShortName\", \"Description\") VALUES (?,?,?)"); 
                        preparedStatement = conn.prepareStatement(sqlStatement.toString());
                        preparedStatement.setString(1, request.getParameter("name").trim());
                        preparedStatement.setString(2, request.getParameter("short_name").trim());
                        preparedStatement.setString(3, request.getParameter("description").trim());
                     } else if ("edit".equals(theAction)) {
                        sqlStatement.append("UPDATE \"Project\" SET ");
                        sqlStatement.append("\"Name\" = ?, ");
                        sqlStatement.append("\"ShortName\" = ?, ");
                        sqlStatement.append("\"Description\" = ? ");
                        sqlStatement.append("WHERE \"Project_id\" = ?;");
                        preparedStatement = conn.prepareStatement(sqlStatement.toString());
                        preparedStatement.setString(1, request.getParameter("name").trim());
                        preparedStatement.setString(2, request.getParameter("short_name").trim());
                        preparedStatement.setString(3, request.getParameter("description").trim());
                        preparedStatement.setInt(4, Integer.parseInt(request.getParameter("id_pro")));
                     }
                    if(preparedStatement.executeUpdate() > 0) {
                        sharedMethods.putAlert(out, "Completed successfully");
                    }
                    break;
                case "employees" :
                    if("add".equals(theAction)) {
                        sqlStatement.append("INSERT INTO \"Employee\" (\"FirstName\", \"LastName\", \"Position\") VALUES (?,?,?)"); 
                        preparedStatement = conn.prepareStatement(sqlStatement.toString());
                        preparedStatement.setString(1, request.getParameter("first_name").trim());
                        preparedStatement.setString(2, request.getParameter("last_name").trim());
                        preparedStatement.setString(3, request.getParameter("position").trim());
                    } else if ("edit".equals(theAction)) {
                        sqlStatement.append("UPDATE \"Employee\" SET ");
                        sqlStatement.append("\"FirstName\" = ?, ");
                        sqlStatement.append("\"LastName\" = ?, ");
                        sqlStatement.append("\"Position\" = ? ");
                        sqlStatement.append("WHERE \"Employee_id\" = ?;");
                        preparedStatement = conn.prepareStatement(sqlStatement.toString());
                        preparedStatement.setString(1, request.getParameter("first_name").trim());
                        preparedStatement.setString(2, request.getParameter("last_name").trim());
                        preparedStatement.setString(3, request.getParameter("position").trim());
                        preparedStatement.setInt(4, Integer.parseInt(request.getParameter("id_emp")));
                    }
                    if(preparedStatement.executeUpdate() > 0) {
                        sharedMethods.putAlert(out, "Completed successfully");
                    }
                    break;
                case "tasks" :
                    if("add".equals(theAction)) {
                        sqlStatement.append("INSERT INTO \"Task\" (\"Project_id\", \"Name\", \"WorkHours\", ");
                        sqlStatement.append("\"StartDate\", \"EndDate\", \"Status\" ) VALUES (?,?,?,?,?,?)"); 
                        preparedStatement = conn.prepareStatement(sqlStatement.toString());
                        preparedStatement.setInt(1, Integer.parseInt(request.getParameter("project_id")));
                        preparedStatement.setString(2, request.getParameter("name").trim());
                        if (request.getParameter("work_hours") != "" && request.getParameter("work_hours") != null) {
                            preparedStatement.setInt(3, Integer.parseInt(request.getParameter("work_hours")));
                        } else {
                            preparedStatement.setInt(3, Integer.parseInt("0"));
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (request.getParameter("start_date") != null && request.getParameter("start_date") != "") {
                            Date convertedDate = sdf.parse(request.getParameter("start_date"));
                            java.sql.Date sqlDate = new java.sql.Date(convertedDate.getTime());
                            preparedStatement.setDate(4, sqlDate);
                        } else {
                            preparedStatement.setDate(4, null);
                        }
                        if (request.getParameter("end_date") != null && request.getParameter("end_date") != "") {
                            Date convertedDate = sdf.parse(request.getParameter("end_date"));
                            java.sql.Date sqlDate = new java.sql.Date(convertedDate.getTime());
                            preparedStatement.setDate(5, sqlDate);
                        } else {
                            preparedStatement.setDate(5, null);
                        }
                        preparedStatement.setString(6, request.getParameter("status").trim());
                    } else if ("edit".equals(theAction)) {
                        sqlStatement.append("UPDATE \"Task\" SET "); 
                        sqlStatement.append("\"Name\" = ?, ");
                        sqlStatement.append("\"WorkHours\" = ?, ");
                        sqlStatement.append("\"StartDate\" = ?, ");
                        sqlStatement.append("\"EndDate\" = ?, ");
                        sqlStatement.append("\"Status\" = ?, ");
                        sqlStatement.append("\"Project_id\" = ? ");
                        sqlStatement.append("WHERE \"Task_id\" = ?;");
                        preparedStatement = conn.prepareStatement(sqlStatement.toString());
                        preparedStatement.setString(1, request.getParameter("name").trim());
                        if (!"".equals(request.getParameter("work_hours")) && request.getParameter("work_hours") != null) {
                            preparedStatement.setInt(2, Integer.parseInt(request.getParameter("work_hours")));
                        } else {
                            preparedStatement.setInt(2, Integer.parseInt("0"));
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                         sdf.setLenient(false);
                        if (request.getParameter("start_date") != null && !"".equals(request.getParameter("start_date"))) {
                            java.util.Date myDate = sdf.parse(request.getParameter("start_date").trim()); 
                            java.sql.Date sqlDate = new java.sql.Date( myDate.getTime() );
                           
                            //System.out.println(sdf.format(sdf.parse(request.getParameter("start_date"))));
                            preparedStatement.setDate(3, sqlDate);
                        } else {
                            preparedStatement.setDate(3, null);
                        }
                        if (request.getParameter("end_date") != null && !"".equals(request.getParameter("end_date"))) {
                            Date convertedDate = sdf.parse(request.getParameter("end_date"));
                            java.sql.Date sqlDate = new java.sql.Date(convertedDate.getTime());
                            preparedStatement.setDate(4, sqlDate);
                        } else {
                            preparedStatement.setDate(4, null);
                        }
                        preparedStatement.setString(5, request.getParameter("status").trim());
                        preparedStatement.setInt(6, Integer.parseInt(request.getParameter("project_id")));
                        preparedStatement.setInt(7, Integer.parseInt(request.getParameter("task_id")));
                    } else if ("assign".equals(theAction)) {
                        String[] executorsValues = request.getParameterValues("executors");
                        if(executorsValues.length > 0) {  
                            for(int i=0; i<executorsValues.length; i++) {
                                sqlStatement.append("INSERT INTO \"Emp_Task\" (\"Employee_id\", \"Task_id\") VALUES (?,?)");
                                preparedStatement = conn.prepareStatement(sqlStatement.toString());
                                preparedStatement.setInt(1, Integer.parseInt(executorsValues[i]));
                                preparedStatement.setInt(2, Integer.parseInt(request.getParameter("task_id")));
                                /*Executing command in loop only executorsValues.length-1
                                 because the last update is being done at the end of switch with alert*/
                                if(i<executorsValues.length-1) {
                                    preparedStatement.executeUpdate();
                                }
                            }
                        }
                    } else if ("remove".equals(theAction)) {
                                sqlStatement.append("DELETE FROM \"Emp_Task\" WHERE ");
                                sqlStatement.append("\"Employee_id\" = ? ");
                                sqlStatement.append("AND \"Task_id\" = ?;");
                                preparedStatement = conn.prepareStatement(sqlStatement.toString());
                                preparedStatement.setInt(1, Integer.parseInt(request.getParameter("executor_id")));
                                preparedStatement.setInt(2, Integer.parseInt(request.getParameter("task_id")));
                    }
                    if(preparedStatement.executeUpdate() > 0) {
                        sharedMethods.putAlert(out, "Completed successfully");
                    } else {
                        sharedMethods.putAlert(out, "Couldn's execute command! Try reconnecting with database or check if you filled required fields!");
                    }
                    break;   
                default:
                    break;                 
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException | ParseException e) {
          Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }      
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}