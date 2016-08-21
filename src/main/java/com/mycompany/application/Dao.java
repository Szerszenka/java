/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MZagrabska
 */
public class Dao {
    
    SharedMethods sharedMethods = new SharedMethods();
    String connectionURL = "jdbc:postgresql://localhost:5432/postgres";
    String login = "postgres";
    String password = "superuser";
    Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
        
    /**
     *
     * @return full list with employees
     * @throws SQLException
     */
    public List<Employee> employeeList() throws SQLException {
        
        List<Employee> employeeList = new ArrayList<Employee>();
        StringBuilder employeeQuery=new StringBuilder("SELECT ");  
        employeeQuery.append("\"Employee_id\", \"FirstName\", \"LastName\", \"Position\" ");
        employeeQuery.append("FROM \"public\".\"Employee\" ORDER BY \"Employee_id\" ASC;");
                    
        try {
            conn = sharedMethods.databaseConnection(connectionURL, login, password);
            preparedStatement = conn.prepareStatement(employeeQuery.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                        Employee employee = new Employee();
                        employee.setEmployeeid(Integer.parseInt(resultSet.getString("Employee_id")));
                        employee.setFirstName(resultSet.getString("FirstName"));
                        employee.setLastName(resultSet.getString("LastName"));
                        employee.setPosition(resultSet.getString("Position"));
                        employeeList.add(employee);
                    }
        } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            if (resultSet != null) try { 
                resultSet.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
            if (preparedStatement != null) try { 
                preparedStatement.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
            if (conn != null) try { 
                conn.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    return employeeList;
    }
    
    /**
     *
     * @param task_id
     * @return list of employees whose are not assigned to the task with task_id
     * @throws SQLException
     */
    public List<Employee> employeeList(Integer task_id) throws SQLException {
        List<Employee> employeeList = new ArrayList<Employee>();
        StringBuilder employeeQuery=new StringBuilder("SELECT ");  
        employeeQuery.append("\"Employee_id\", \"LastName\" ");
        employeeQuery.append("FROM \"public\".\"Employee\";");
        StringBuilder emp_taskQuery=new StringBuilder("SELECT ");  
        emp_taskQuery.append("\"Employee_id\", \"Task_id\" ");
        emp_taskQuery.append("FROM \"public\".\"Emp_Task\" ");
        emp_taskQuery.append("WHERE \"Task_id\" = ?;");
        try {
            conn = sharedMethods.databaseConnection(connectionURL, login, password);
            preparedStatement = conn.prepareStatement(employeeQuery.toString());
            resultSet = preparedStatement.executeQuery();
            PreparedStatement preparedStatementTask = conn.prepareStatement(emp_taskQuery.toString());
            preparedStatementTask.setInt(1, task_id);
            ResultSet resultTask = preparedStatementTask.executeQuery();
            List<Integer> empTaskList = new ArrayList<Integer>();
            // lista wszystki id employee nalezacych do danego tasku
            while(resultTask.next()) {
                empTaskList.add(Integer.parseInt(resultTask.getString("Employee_id")));
            }
            while (resultSet.next()){
                if(!empTaskList.contains(Integer.parseInt(resultSet.getString("Employee_id")))) {
                        Employee employee = new Employee();
                        employee.setEmployeeid(Integer.parseInt(resultSet.getString("Employee_id")));
                        employee.setLastName(resultSet.getString("LastName"));
                        employeeList.add(employee);
                    }
            }
        } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            if (resultSet != null) try { 
                resultSet.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
            if (preparedStatement != null) try { 
                preparedStatement.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
            if (conn != null) try { 
                conn.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    return employeeList;
    }
    
    /**
     *
     * @return full list of projects
     * @throws SQLException
     * @throws ParseException
     */
    public List<Project> projectList() throws SQLException, ParseException {

        List<Project> projectList = new ArrayList<Project>();
        StringBuilder projectQuery = new StringBuilder("SELECT ");
        projectQuery.append("\"Project_id\", \"Name\", \"ShortName\", \"Description\" ");
        projectQuery.append("FROM \"public\".\"Project\" ORDER BY \"Project_id\" ASC;");
        try {
            conn = sharedMethods.databaseConnection(connectionURL, login, password);
            preparedStatement = conn.prepareStatement(projectQuery.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setProjectid(Integer.parseInt(resultSet.getString("Project_id")));
                project.setName(resultSet.getString("Name"));
                project.setShortName(resultSet.getString("ShortName"));
                project.setDescription(resultSet.getString("Description"));

                List<Task> taskList = new ArrayList<Task>();
                StringBuilder taskQuery = new StringBuilder("SELECT ");
                taskQuery.append("\"Task_id\", \"Project_id\", \"Name\", \"WorkHours\", ");
                taskQuery.append("\"StartDate\", \"EndDate\", \"Status\" FROM \"public\".\"Task\" ");
                taskQuery.append("WHERE \"Task\".\"Project_id\" = ?");
                taskQuery.append("ORDER BY \"Task_id\" ASC;");
                PreparedStatement preparedStatementTask = conn.prepareStatement(taskQuery.toString());
                preparedStatementTask.setInt(1, Integer.parseInt(resultSet.getString("Project_id")));
                ResultSet resultTask = preparedStatementTask.executeQuery();
                if (resultTask.next()) {
                    do {
                        Task task = new Task();
                        task.setTaskid(Integer.parseInt(resultTask.getString("Task_id")));
                        task.setProjectid(Integer.parseInt(resultTask.getString("Project_id")));
                        task.setName((resultTask.getString("Name")));
                        if (resultTask.getString("WorkHours") != null) {
                            task.setWorkHours(Integer.parseInt(resultTask.getString("WorkHours")));
                        } else {
                            task.setWorkHours(0);
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (resultTask.getString("StartDate") != null) {
                            Date convertedDate = sdf.parse(resultTask.getString("StartDate"));
                            task.setStartDate(convertedDate);
                        } else {
                            task.setStartDate(null);
                        }
                        if (resultTask.getString("EndDate") != null) {
                            Date convertedDate = sdf.parse(resultTask.getString("EndDate"));
                            task.setEndDate(convertedDate);
                        } else {
                            task.setStartDate(null);
                        }
                        task.setStatus((resultTask.getString("Status")));
                        taskList.add(task);
                    } while (resultTask.next());
                } else {
                    // No data, TaskList is null
                }
                project.setTaskList(taskList);
                projectList.add(project);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null) try { 
                resultSet.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
            if (preparedStatement != null) try { 
                preparedStatement.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
            if (conn != null) try { 
                conn.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    return projectList;
    }
    
    /**
     *
     * @return full list of tasks
     * @throws SQLException
     * @throws ParseException
     */
    public List<Task> taskList() throws SQLException, ParseException {
        
        List<Task> taskList = new ArrayList<Task>();
        StringBuilder taskQuery=new StringBuilder("SELECT ");  
        taskQuery.append("\"Task_id\", \"Project_id\", \"Name\", \"WorkHours\", ");
        taskQuery.append("\"StartDate\", \"EndDate\", \"Status\" FROM \"public\".\"Task\" ");
        taskQuery.append("ORDER BY \"Task_id\" ASC;");
                    
        try {
            conn = sharedMethods.databaseConnection(connectionURL, login, password);
            preparedStatement = conn.prepareStatement(taskQuery.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                
                StringBuilder projectQuery = new StringBuilder("SELECT ");
                projectQuery.append("\"ShortName\" FROM \"public\".\"Project\" ");
                projectQuery.append("WHERE \"Project\".\"Project_id\" = ?;");
                PreparedStatement preparedStatementProject = conn.prepareStatement(projectQuery.toString());
                preparedStatementProject.setInt(1, Integer.parseInt(resultSet.getString("Project_id")));
                ResultSet resultProject = preparedStatementProject.executeQuery();
                if (resultProject.next()) {
                    task.setProjectShortName(resultProject.getString("ShortName"));
                } else {
                    task.setProjectShortName(null);
                }     
                task.setTaskid(Integer.parseInt(resultSet.getString("Task_id")));
                task.setProjectid(Integer.parseInt(resultSet.getString("Project_id")));
                task.setName((resultSet.getString("Name")));
                if (resultSet.getString("WorkHours") != null) {
                    task.setWorkHours(Integer.parseInt(resultSet.getString("WorkHours")));
                } else {
                    task.setWorkHours(0);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (resultSet.getString("StartDate") != null) {
                    Date convertedDate = sdf.parse(resultSet.getString("StartDate"));
                    task.setStartDate(convertedDate);
                } else {
                    task.setStartDate(null);
                }
                if (resultSet.getString("EndDate") != null) {
                    Date convertedDate = sdf.parse(resultSet.getString("EndDate"));
                    task.setEndDate(convertedDate);
                } else {
                    task.setStartDate(null);
                }
                task.setStatus((resultSet.getString("Status")));
                
                List<Employee> employeeList = new ArrayList<Employee>();
                StringBuilder employeeQuery = new StringBuilder("SELECT ");
                employeeQuery.append("\"Employee\".\"Employee_id\", \"Employee\".\"LastName\" ");
                employeeQuery.append("FROM \"public\".\"Employee\", \"public\".\"Emp_Task\" ");
                employeeQuery.append("WHERE \"Emp_Task\".\"Task_id\" = ? ");
                employeeQuery.append("AND \"Employee\".\"Employee_id\" = \"Emp_Task\".\"Employee_id\" ");
                employeeQuery.append("ORDER BY \"Employee_id\" ASC;");
                PreparedStatement preparedStatementEmployee = conn.prepareStatement(employeeQuery.toString());
                preparedStatementEmployee.setInt(1, Integer.parseInt(resultSet.getString("Task_id")));
                ResultSet resultEmployee = preparedStatementEmployee.executeQuery();
                if (resultEmployee.next()) {
                    do {
                        Employee employee = new Employee();
                        employee.setEmployeeid(Integer.parseInt(resultEmployee.getString("Employee_id")));
                        employee.setLastName(resultEmployee.getString("LastName"));
                        employeeList.add(employee);
                    } while (resultEmployee.next());
                } else {
                    // No data, TaskList is null
                }
                task.setEmployeeList(employeeList);
                taskList.add(task);
            }
        } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            if (resultSet != null) try { 
                resultSet.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
            if (preparedStatement != null) try { 
                preparedStatement.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
            if (conn != null) try { 
                conn.close(); 
            } catch (SQLException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    return taskList;
    }
    
}

