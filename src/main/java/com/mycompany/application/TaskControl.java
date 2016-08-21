/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author MZagrabska
 */
public class TaskControl extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * Controller to redirect to suitable jsp page, also when there is no need to popup form
     * in case of deleting task or removing employee form task it handle it and redirect with reload parent
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        SharedMethods sharedMethods = new SharedMethods();
        String connectionURL = "jdbc:postgresql://localhost:5432/postgres";
        String login = "postgres";
        String password = "superuser";
        Connection conn = null;
        String checkAdd = request.getParameter("add");
        String checkEdit = request.getParameter("edit");
        String checkAssign = request.getParameter("assign");
        String[] parametrsRemove = request.getParameterValues("remove");
        String[] parametrsDelete = request.getParameterValues("delete");
        try {
            conn = sharedMethods.databaseConnection(connectionURL, login, password);
            String deleteStm;
            if (checkAdd != null) {
                Dao dao = new Dao();
                List<Project> projectList = dao.projectList();
                request.setAttribute("projectList", projectList);
                getServletConfig().getServletContext().getRequestDispatcher(
                        "/add_task.jsp").forward(request, response);
            } else if (parametrsRemove != null) {
                List<String> executorList = Arrays.asList(request.getParameter("idemp").split("\\s*,\\s*")); //separating by comma
                for (String executor : executorList) {
                    deleteStm = "DELETE FROM \"Emp_Task\" WHERE "
                            + "\"Employee_id\" = " + Integer.parseInt(executor) + " "
                            + "AND \"Task_id\" = " + Integer.parseInt(request.getParameter("idtask")) + ";";
                    Statement stmt = (Statement) conn.createStatement();
                    stmt.executeUpdate(deleteStm);
                }
                //deleteStm = "DELETE FROM \"Emp_Task\" WHERE "
                //        + "\"Employee_id\" = " + Integer.parseInt(request.getParameter("idemp")) + " "
                //        + "AND \"Task_id\" = " + Integer.parseInt(request.getParameter("idtask")) + ";";
                /*Reload the page after successfully deleting record*/
                response.sendRedirect("tasks?list=tasks");
                out.println("alert(Connect failed)");
                //Statement stmt=(Statement)conn.createStatement();
                //stmt.executeUpdate(deleteStm);
            } else if (checkEdit != null) {
                Dao dao = new Dao();
                List<Project> projectList = dao.projectList();
                request.setAttribute("projectList", projectList);
                getServletConfig().getServletContext().getRequestDispatcher(
                        "/edit_task.jsp").forward(request, response);
            } else if (checkAssign != null) {
                Dao dao = new Dao();
                List<Employee> employeeList = dao.employeeList(Integer.parseInt(request.getParameter("tasks")));
                request.setAttribute("employeeList", employeeList);
                getServletConfig().getServletContext().getRequestDispatcher(
                        "/assign_task.jsp").forward(request, response);
            } else if (parametrsDelete != null) {
                deleteStm = "DELETE FROM \"Task\" WHERE \"Task_id\" = " + parametrsDelete[1] + ";";
                /*Reload the page after successfully deleting record*/
                response.sendRedirect("tasks?list=tasks");
                out.println("alert(Connect failed)");
                Statement stmt = (Statement) conn.createStatement();
                stmt.executeUpdate(deleteStm);
            }
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, e);
        } catch (ParseException ex) {
            Logger.getLogger(TaskControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
