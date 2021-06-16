package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.*;
import dao.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);

        String password = data.get("password").getAsString();
        String login = data.get("login").getAsString();

        try {
            List<User> users = new ArrayList<>();
            users = UserDAO.getUserFromDB(login, password);
            if (users.isEmpty()) return;
            String json = new Gson().toJson(users.get(0));
            response.addHeader("Access-Control-Allow-Origin", "http://localhost:5000");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }

}