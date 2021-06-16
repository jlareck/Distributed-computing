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

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject act = new Gson().fromJson(req.getReader(), JsonObject.class);
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:5000");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        switch (act.get("action").getAsString()) {
            case Action.REQUEST_BOOK:
                BookRequestDAO.addRequest(act.get("userId").getAsInt(), act.get("bookId").getAsInt());
                break;
            case Action.BOOKS_LIST: {
                ArrayList<Book> books = BookDAO.getBooksFromDB(Constants.SELECT_ALL_INT, Constants.SELECT_ALL_STR);
                String jsonBooks = new Gson().toJson(books);
                resp.getWriter().write(jsonBooks);
            }
            break;
            case Action.REQUESTS_LIST: {
                ArrayList<BookRequest> reqs = BookRequestDAO.getBookRequests(act.get("userId").getAsInt(),
                        Constants.SELECT_ALL_INT);
                String json_reqs = new Gson().toJson(reqs);
                resp.getWriter().write(json_reqs);
            }
            break;
            case Action.TAKE_BOOK: {
                long userId = act.get("userId").getAsLong();
                long bookId = act.get("bookId").getAsLong();
                Answer answer;
                if (ReadingDAO.getReadings(userId, Constants.SELECT_ALL_INT).size() == 0) {
                    ArrayList<Book> books = BookDAO.getBooksFromDB(bookId, Constants.SELECT_ALL_STR);
                    if (!books.isEmpty() && books.get(0).getStock() > 0) {
                        ReadingDAO.addReading(userId, bookId);
                        BookDAO.takeBook(bookId);
                        answer = new Answer(true, "message");
                    } else {
                        answer = new Answer(false, "no such book");
                    }
                } else {
                    answer = new Answer(false, "you're already reading one");
                }
                String jsonAnswer = new Gson().toJson(answer);
                resp.getWriter().write(jsonAnswer);
            }
            break;
            case Action.RETURN_BOOK: {
                int userId = act.get("userId").getAsInt();
                int bookId = act.get("bookId").getAsInt();
                ReadingDAO.deleteReading(userId, bookId);
                BookDAO.returnBook(bookId);
            }
            break;
            case Action.GET_READING_BOOK: {
                int userId = act.get("userId").getAsInt();
                ArrayList<Reading> readings = ReadingDAO.getReadings(userId, Constants.SELECT_ALL_INT);
                if (readings.size() != 0) {
                    String jsonReading = new Gson().toJson(readings.get(0));
                    resp.getWriter().write(jsonReading);

                } else {
                    Answer answer = new Answer(false, "No readings");
                    String json_answer = new Gson().toJson(answer);
                    resp.getWriter().write(json_answer);
                }
            }
            break;
            case Action.UNREQUEST_BOOK: {
                int userId = act.get("userId").getAsInt();
                int bookId = act.get("bookId").getAsInt();
                BookRequestDAO.deleteRequest(userId, bookId);
            }
            break;
            case Action.TAKE_HOME: {
                int userId = act.get("userId").getAsInt();
                int bookID = act.get("bookId").getAsInt();
                Answer answer;
                if (ReadingDAO.getReadings(userId, Constants.SELECT_ALL_INT).size() != 0) {
                    answer = new Answer(false, "You are reading book rn");
                } else {
                    ArrayList<BookRequest> reqs = BookRequestDAO.getBookRequests(userId, bookID);
                    if (reqs.size() == 0) {
                        answer = new Answer(false, "There was no request");
                    } else {
                        BookRequest bookRequest = reqs.get(0);
                        if (!bookRequest.isAccepted()) {
                            answer = new Answer(false, "Not accepted");
                        } else {
                            ReadingDAO.addReading(userId, bookID);
                            BookRequestDAO.deleteRequest(userId, bookID);
                            BookDAO.takeBook(bookID);
                            answer = new Answer(true, "Success");
                        }
                    }
                }
                String jsonAnswer = new Gson().toJson(answer);
                resp.getWriter().write(jsonAnswer);
            }
        }
    }
}