package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.*;
import dao.*;

@WebServlet("/librarian")
public class LibrarianServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject act = new Gson().fromJson(req.getReader(), JsonObject.class);
        System.out.println("wow");
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:5000");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        switch (act.get("action").getAsString()) {
            case Action.ACCEPT_BOOK:
                BookRequestDAO.acceptRequest(act.get("id_user").getAsInt(), act.get("id_book").getAsInt());
                break;
            case Action.REQUESTS_LIST: {
                ArrayList<BookRequest> reqs = BookRequestDAO.getBookRequests(Constants.SELECT_ALL_INT,
                        Constants.SELECT_ALL_INT);
                String jsonReqs = new Gson().toJson(reqs);
                resp.getWriter().write(jsonReqs);
            }
            break;
            case Action.REFUSE_BOOK: {
                int userId = act.get("id_user").getAsInt();
                int bookId = act.get("id_book").getAsInt();
                ArrayList<BookRequest> reqs = BookRequestDAO.getBookRequests(userId, bookId);
                Answer answer;
                if (reqs.isEmpty()) {
                    answer = new Answer(false, "No such request");
                } else if (reqs.get(0).isAccepted()) {
                    answer = new Answer(false, "Already accepted");
                } else {
                    BookRequestDAO.deleteRequest(userId, bookId);
                    answer = new Answer(true, "Success");
                }
                String jsonAns = new Gson().toJson(answer);
                resp.getWriter().write(jsonAns);
            }
            break;
        }
    }
}
