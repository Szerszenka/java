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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
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
public class ProjectControl extends HttpServlet {


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
     * in case of deleting it handle it and redirect with reload parent
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exp = "";
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String connectionURL = "jdbc:postgresql://localhost:5432/postgres";
        String checkAdd = request.getParameter("add");
        String checkEdit = request.getParameter("edit");
        String[] parametrs = request.getParameterValues("delete");
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(connectionURL, "postgres", "superuser");
            String deleteStm = "";
            if (checkAdd != null) {
               getServletConfig().getServletContext().getRequestDispatcher(
                    "/add_project.jsp").forward(request,response);
            } else if (checkEdit != null) {
                getServletConfig().getServletContext().getRequestDispatcher(
                    "/edit_project.jsp").forward(request,response);
            } else if (parametrs[1] != null) {
                /*Counting the number of tasks that are assigned to project that is being deleted
                  in case if any of comfirmation is needed*/
                ArrayList taskList = new ArrayList();
                try {
                    Statement tasksStatement = conn.createStatement() ;
                    ResultSet resultSet = tasksStatement.executeQuery("SELECT \"Task\".\"Task_id\" FROM \"Task\" WHERE \"Task\".\"Project_id\" = " + parametrs[1] + ";");
                    while(resultSet.next()) {
                        taskList.add(resultSet.getString(1));
                    }
                } catch (SQLException ex) {
                    out.println("SQLException, couldn't execute SQL command! " + ex);
                }                
                if(taskList.size() > 0) {
                /*If the project have some task is possible to make some comfiramtion here*/    
                } 
                deleteStm = "DELETE FROM \"Project\" WHERE \"Project_id\" = " + parametrs[1] + ";";
                /*Reload the page after successfully deleting record*/
                response.sendRedirect("projects?list=projects");
                Statement stmt=(Statement)conn.createStatement();
                stmt.executeUpdate(deleteStm); 
                SQLWarning warning = stmt.getWarnings();
                while (warning != null) {
                    System.out.println(warning.getMessage());
                    warning = warning.getNextWarning();
                }
            }
            conn.close(); 
        } catch (SQLException | ClassNotFoundException e) {
          Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, e);
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
