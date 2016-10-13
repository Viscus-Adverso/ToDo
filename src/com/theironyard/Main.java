package com.theironyard;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    public static void createTable(Connection conn) throws SQLException{
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABlE If NOT EXISTS  to_dos (id IDENTITY, text VARCHAR, is_done BOOLEAN)");
    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTable(conn);


        Scanner scanner = new Scanner(System.in);

        while (true) {
            boolean isLoggedIn = true;
            while (isLoggedIn) {
                System.out.println("1. Create to-do item");
                System.out.println("2. Check/Uncheck to-do item");
                System.out.println("3. List all to-do items");
                System.out.println("4. delete item");
                System.out.println("5. Log out");

                String option = (scanner.nextLine());

                switch (option) {
                    case "1":
                        addTodo(conn, scanner);
                        break;
                    case "2":
                        toggleTodo(conn, scanner);
                        break;
                    case "3":
                        listTodo(conn);
                        break;
                    case "4" :
                        deleteTodo(conn, scanner);
                        break;
                    case "5":
                        isLoggedIn = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        }
    }


    public static void addTodo( Connection conn, Scanner scanner) throws SQLException {
        System.out.println("Enter your to-do list:");
        String text = scanner.nextLine();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO to_dos VALUES(NULL, ?, ?)");
        stmt.setString(1, text);
        stmt.setBoolean(2, false);
        stmt.execute();
    }

    public static void toggleTodo(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("Which item do you want to toggle");
        int i = Integer.valueOf(scanner.nextLine());

        PreparedStatement stmt = conn.prepareStatement("UPDATE to_dos SET is_done = NOt is_done WHERE id = ?");
        stmt.setInt(1, i);
        stmt.execute();
    }
    public static void listTodo(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM to_dos");
        ResultSet results = stmt.executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        while (results.next()) {
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");
            Item item = new Item(id, text, isDone);
            items.add(item);
        }

        for (int j = 0; j < items.size(); j++) {
            Item item3 = items.get(j);
            String checkbox = "[ ]";
            if (item3.isDone) {
                checkbox = "[x]";
            }
            System.out.printf("%s %s. %s\n", checkbox, item3.id, item3.text);
        }
    }

    public static void deleteTodo (Connection conn, Scanner scanner) throws SQLException {
        System.out.println("which item do you want to delete?");
        int i = Integer.valueOf(scanner.nextLine());

        PreparedStatement stmt = conn.prepareStatement("DELETE FROM to_dos Where id = ?");
        stmt.setInt(1, i);
        stmt.execute();
    }
}
