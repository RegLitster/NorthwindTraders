package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = args[0];
        String password = args[1];
        boolean run = true;

        while (run) {
            System.out.println("""
                    What do you want to do?
                    1) Display all products
                    2) Display all customers
                    0) Exit
                    Select an option:""");

            String input = scanner.nextLine();

            switch (input) {
                case "0":
                    System.out.println("Exiting program.");
                    run = false;
                    System.exit(0);
                    break;
                case "1":
                    displayProducts(url, user, password);
                    break;
                case "2":
                    displayCustomers(url, user, password);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private static void displayProducts(String url, String user, String password) {
        String query = "SELECT * FROM Products";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(query);
            results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("ProductID");
                String name = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");

                System.out.println("Product Id: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Stock: " + stock);
                System.out.println("------------------");

            }
        } catch (SQLException e) {
            System.out.println("Error accessing Products table.");
            e.printStackTrace();
        } finally {


            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private static void displayCustomers(String url, String user, String password) {
        String query = "SELECT * FROM Customers ORDER BY Country";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(query);
            results = statement.executeQuery();

            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.println("Contact Name: " + contactName);
                System.out.println("Company Name: " + companyName);
                System.out.println("City: " + city);
                System.out.println("Country: " + country);
                System.out.println("Phone: " + phone);
                System.out.println("------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error accessing Customers table.");
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


