package com.quiz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(QuizServlet.class.getName());
   

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            List<Question> questions = (List<Question>) session.getAttribute("questions");
            int score = calculateScore(request, questions);
            session.setAttribute("score", score);
            response.sendRedirect("result");
        } else {
            response.sendRedirect("login.html");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            List<Question> questions = getQuestionsFromDatabase();
            session.setAttribute("questions", questions);
            request.getRequestDispatcher("quiz.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.html");
        }
    }

    
    private List<Question> getQuestionsFromDatabase() {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM questions";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String questionText = rs.getString("question_text");
                        String[] options = new String[]{
                            rs.getString("option1"),
                            rs.getString("option2"),
                            rs.getString("option3"),
                            rs.getString("option4")
                        };
                        int correctOption = rs.getInt("correct_option");
                        questions.add(new Question(id, questionText, options, correctOption));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching questions from database", e);
        }
        return questions;
    }
    private int calculateScore(HttpServletRequest request, List<Question> questions) {
        int score = 0;
        for (Question question : questions) {
            String userAnswer = request.getParameter("q" + question.getId());
            if (userAnswer != null && Integer.parseInt(userAnswer) == question.getCorrectOption()) {
                score++;
            }
        }
        return score;
    }
}

