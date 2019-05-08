/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.caesar.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lab.caesar.web.model.CaesarModel;

/**
 *
 * @author Piotr Kapski
 * @version 5.0
 */
@WebServlet(name = "History", urlPatterns = {"/History"})
public class History extends HttpServlet {

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
        CaesarModel model = (CaesarModel) session.getAttribute("CaesarModel");
        Cookie[] cookies = request.getCookies();
        Connection connection = null;
        if ((Connection) session.getAttribute("connection") == null) {
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
        connection = (Connection) session.getAttribute("connection");

        /* TODO output your page here. You may use following sample code. */
        out.println("<h2>History</h2>");
        out.println("<hr>");
        if (cookies == null && false) {
            out.println("There is no history!");
        } else {
            if (cookies != null) {
                out.println("<h3>Most recent encoding/decoding</h3><br>");
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastVisit")) {
                        out.println(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                        break;
                    }
                }
            }
            out.println("<h3>More history:</h3><br>");
            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM History");

                if (rs.next() == false) {
                    out.println("No history");
                } else {
                    out.println("ID &emsp;&emsp; Type &emsp;&emsp; Message &emsp;&emsp; Key<br>");
                    out.println(rs.getInt("ID") + "&emsp; &emsp;&emsp;");
                    out.println(rs.getString("Type") + "&emsp; &emsp;");
                    out.println(rs.getString("Message") + "&emsp; &emsp;");
                    out.println(rs.getInt("Code_key") + "&emsp; &emsp; &emsp;" + "<br>");
                }

                while (rs.next()) {
                    out.println(rs.getInt("ID") + "&emsp; &emsp;&emsp;");
                    out.println(rs.getString("Type") + "&emsp; &emsp;");
                    out.println(rs.getString("Message") + "&emsp; &emsp;");
                    out.println(rs.getInt("Code_key") + "&emsp; &emsp; &emsp;" + "<br>");
                }
            } catch (SQLException sqle) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "SQL error!");
            }
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
