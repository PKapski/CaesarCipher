/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.caesar.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lab.caesar.web.model.CaesarModel;
import pl.polsl.lab.caesar.web.model.InvalidInputException;
import java.sql.*;

/**
 *
 * @author Piotr Kapski
 * @version 5.0
 */
@WebServlet(name = "Caesar", urlPatterns = {"/Caesar"})
public class Caesar extends HttpServlet {

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

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        //Cookie[] cookies = request.getCookies();

        if ((CaesarModel) session.getAttribute("CaesarModel") == null) {
            /**
             * Creating model (once) and passing it to the session
             */
            CaesarModel model = new CaesarModel();
            session.setAttribute("CaesarModel", model);
            /**
             * Connection variable responsible for commmunication between
             * program and database
             */
            Connection connection = null;
            try {
                Class.forName(this.getInitParameter("driver"));
                connection = DriverManager.getConnection(this.getInitParameter("url"), this.getInitParameter("user"), this.getInitParameter("password"));
            } catch (SQLException sqle) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, sqle.getMessage());
            } catch (ClassNotFoundException cnfe) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Class not found!");
            }
            session.setAttribute("connection", connection);
        }
        CaesarModel model = (CaesarModel) session.getAttribute("CaesarModel");
        Connection connection = (Connection) session.getAttribute("connection");
        String message = request.getParameter("sentence");
        String output = "";
        Integer id = 1;
        /**
         * Temp variable that stores message that will be passed to Cookie
         */
        String temp = "Message: " + message;
        try {
            Statement statement = connection.createStatement();
            Integer key = Integer.parseInt(request.getParameter("key"));
            temp = temp + "<br>Key: " + key;
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM History");
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }

            if (message.length() == 0) {
                response.sendError(response.SC_BAD_REQUEST, "Parameters cannnot be empty!");
            } else {
                if (request.getParameter("action").equals("Encode")) {
                    output = model.encode(message, key);
                    out.println("Encoded message: " + output);
                    temp = temp + "<br>Encoded message: " + output;
                    statement.executeUpdate("INSERT INTO History VALUES ("
                            + id + ",'"
                            + "Encode','"
                            + message + "',"
                            + key + ")");
                } else {
                    output = model.decode(message, key);
                    out.println("Decoded message: " + output);
                    temp = temp + "<br>Decoded message: " + output;
                    statement.executeUpdate("INSERT INTO History VALUES ("
                            + id + ",'"
                            + "Decode','"
                            + message + "',"
                            + key + ")");
                }
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid key!");
        } catch (InvalidInputException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input!");
        } catch (SQLException sqle) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "SQL error!");
        }
        Cookie cookie = new Cookie("lastVisit", URLEncoder.encode("Date: " + new java.util.Date().toString() + "<br>" + temp, "UTF-8"));
        response.addCookie(cookie);

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
        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

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
