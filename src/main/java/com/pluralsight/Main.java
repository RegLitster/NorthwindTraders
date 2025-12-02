package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/";
    private static Connection connection = null;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String user = args[0];
        String password = args[1];

        loadConnection("northwind", user, password);

        boolean run = true;

        while (run) {
            System.out.println("""
                    What do you want to do?
                    1) Display all products
                    2) Display all customers
                    3) Display all categories
                    0) Exit
                    Select an option:""");

            String input = scanner.nextLine();

            switch (input) {
                case "0":
                    System.out.println("Exiting program.");
                    run = false;
                    break;
                case "1":
                    displayProducts();
                    break;
                case "2":
                    displayCustomers();
                    break;
                case "3":
                    displayCategories();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayProducts() {
        String query = "SELECT * FROM Products";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                System.out.println("Product Id: " + results.getInt("ProductID"));
                System.out.println("Name: " + results.getString("ProductName"));
                System.out.println("Price: " + results.getDouble("UnitPrice"));
                System.out.println("Stock: " + results.getInt("UnitsInStock"));
                System.out.println("------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayCustomers() {
        String query = "SELECT * FROM Customers ORDER BY Country";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                System.out.println("Contact Name: " + results.getString("ContactName"));
                System.out.println("Company Name: " + results.getString("CompanyName"));
                System.out.println("City: " + results.getString("City"));
                System.out.println("Country: " + results.getString("Country"));
                System.out.println("Phone: " + results.getString("Phone"));
                System.out.println("------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayCategories() {
        String query = "SELECT * FROM Categories";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                System.out.println("Category ID: " + results.getInt("CategoryID"));
                System.out.println("Name: " + results.getString("CategoryName"));
                System.out.println("Description: " + results.getString("Description"));
                System.out.println("------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadConnection(String database, String username, String password) {
        try {
            connection = DriverManager.getConnection(url + database, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
