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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MZagrabska
 * Main servlet
 */
public class EmployeeControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddEmployee</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddEmployee at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String connectionURL = "jdbc:postgresql://localhost:5432/postgres";
        /*three string for checking what kind of parameter did the controller get*/
        String checkAdd = request.getParameter("add");
        String checkEdit = request.getParameter("edit");
        /*if the parameter was delete the controller took as second value employee_id*/
        String[] parametrs = request.getParameterValues("delete");
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(connectionURL, "postgres", "superuser");
            String deleteStm = "";
            if (checkAdd != null) {
               getServletConfig().getServletContext().getRequestDispatcher(
                    "/add_employee.jsp").forward(request,response);
            } else if (checkEdit != null) {
                getServletConfig().getServletContext().getRequestDispatcher(
                    "/edit_employee.jsp").forward(request,response);
            } else if (parametrs[1] != null) {
                deleteStm = "DELETE FROM \"Employee\" WHERE \"Employee_id\" = " + parametrs[1] + ";";
                /*Reload the page after successfully deleting record*/
                response.sendRedirect("employees?list=employees");
                Statement stmt=(Statement)conn.createStatement();
                stmt.executeUpdate(deleteStm); 
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
