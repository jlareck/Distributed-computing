package com.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSocketTask5 {

    private static int port = 9876;

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Scanner scan = new Scanner(System.in);
        while (true)
        {
            System.out.println("Input a num of operation");
            System.out.println("[1] - Get books by author");
            System.out.println("[2] - Get books by publisher");
            System.out.println("[3] - Get books after given year");
            //establish socket connection to server
            socket = new Socket(host.getHostName(), port);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            int commandIndex = scan.nextInt();
            if (commandIndex == 4)
            {
                socket = new Socket(host.getHostName(), port);
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending close Request");
                oos.writeInt(commandIndex); oos.flush();
                break;
            }
            switch (commandIndex) {
                case 1 -> {
                    System.out.println("Enter author");
                    Scanner scan2 = new Scanner(System.in);
                    String author = scan2.nextLine();
                    String message = "" + commandIndex + "#" + author;
                    oos.writeBytes(message);
                    oos.flush();
                }
                case 2 -> {
                    System.out.println("Enter publisher");
                    Scanner scan2 = new Scanner(System.in);
                    String publisher = scan2.nextLine();
                    String message = "" + commandIndex+"#" +publisher;
                    oos.writeBytes(message);
                    oos.flush();
                }

                case 3 -> {
                    System.out.println("Enter year");
                    Scanner scan2 = new Scanner(System.in);
                    int year = scan2.nextInt();
                    String message = "" + commandIndex+"#" +year;
                    oos.writeBytes(message);
                    oos.flush();
                }
            }
            System.out.println("Receiving answers");
            ois = new ObjectInputStream(socket.getInputStream());
            ArrayList<Book> answer = new ArrayList<>();
            answer = (ArrayList<Book>) ois.readObject();

            for (Book book: answer)
            {
                System.out.println(book);
            }
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
        oos.writeInt(4);
        System.out.println("Shutting down client!!");
    }
}
