package com.example;

import java.io.*;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import java.io.Serializable;
import java.util.List;


class Callback
{
    public boolean bShouldEnded = false;
}

class BookInteractor implements Runnable
{
    BookInteractor(Socket inSocket, Callback inC, List<Book> books)
    {
        c = inC;
        socket = inSocket;
        this.books = books;
    }
    @Override
    public void run() {
        try {
            InputStreamReader ois = new InputStreamReader(socket.getInputStream());
            System.out.println("Receiving input");
            //convert ObjectInputStream object to String
            BufferedReader reader = new BufferedReader(ois);
            String message = reader.readLine();
            String splitMessage[] = message.split("#");
            String commandIndex = splitMessage[0];
            System.out.println("Command " + splitMessage[0]);

            if (commandIndex.equals("4"))
            {
                System.out.println("Close command");
                c.bShouldEnded = true;
                return;
            }
            List<Book> result = new ArrayList<>();
            switch (commandIndex) {
                case "1" -> {
                    String author = splitMessage[1];
                    for (Book book: books) {
                        if (book.authors.contains(author)) {
                            result.add(book);
                        }
                    }
                }
                case "2" -> {
                    String publisher = splitMessage[1];
                    for (Book book: books) {
                        if (book.publisher.equals(publisher)) {
                            result.add(book);
                        }
                    }
                }
                case "3" -> {
                    String year = splitMessage[1];
                    for (Book book: books) {
                        if (book.year > Integer.parseInt(year)) {
                            result.add(book);
                        }
                    }
                }
            }
            //create ObjectOutputStream object
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            oos.writeObject(result);

            //close resources
            ois.close();
            oos.close();
            socket.close();
        }
        catch (IOException e)
        {

        }
    }

    Socket socket;
    Callback c;
    List<Book> books;
}

public class ServerSocketTask5 {

    private static ServerSocket server;
    private static int port = 9876;

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        List<Book> books = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        authors.add("author1");
        authors.add("authors2");
        books.add(new Book(1,"name1",authors, "pub1", 1990, 10, 15));
        books.add(new Book(2,"name2",authors, "pub2", 1991, 11, 15));
        books.add(new Book(3,"name3",authors, "pub3", 1992, 12, 15));
        books.add(new Book(4,"name4",authors, "pub4", 1993, 13, 15));

        while(!c.bShouldEnded){
            System.out.println("Waiting for the client request");
            Socket socket = server.accept();
            BookInteractor calc = new BookInteractor(socket, c, books);
            calc.run();
        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }
    public static Callback c = new Callback();
}
