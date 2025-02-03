package com.quiz;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            Integer score = (Integer) session.getAttribute("score");
            if (score != null) {
                request.setAttribute("score", score);
                request.getRequestDispatcher("result.jsp").forward(request, response);
            } else {
                response.sendRedirect("quiz");
            }
        } else {
            response.sendRedirect("login.html");
        }
    }
}

