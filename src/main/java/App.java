package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class App {
  final String databaseURL = "jdbc:postgresql://localhost/";
  final static String user = "postgres";
  final static String password = "";

  Connection connection;
  PreparedStatement preparedStatement;
  ResultSet rs;
  Statement statement;
  

  public App() throws Exception{
    Class.forName("org.postgresql.Driver");

   try {
     connection = DriverManager.getConnection(databaseURL, user, password);
     statement = connection.createStatement();
     statement.execute("DROP TABLE IF EXISTS test;");
     statement.execute("CREATE TABLE test (city varchar(20), state char(2));");
   } catch (java.sql.SQLException ex) {
     System.out.println("Failed to set up database.");
   } 
  
  }
  
  public void insertData() {
    try {
      connection = DriverManager.getConnection(databaseURL, user, password);
      preparedStatement = connection.prepareStatement("INSERT INTO test (city, state) VALUES (?, ?);");
      preparedStatement.setString(1, "Seattle");
      preparedStatement.setString(2, "WA");
      preparedStatement.executeUpdate();
    } catch (java.sql.SQLException ex) {
    } finally {
      try {
        preparedStatement.close();
      } catch (java.sql.SQLException ex) {
        preparedStatement = null;
      }
      try {
        connection.close();
      } catch (java.sql.SQLException ex) {
        connection = null;
      }
    }
  }
  public String getData() {
    String toReturn = "";
    try {
      connection = DriverManager.getConnection(databaseURL, user, password);
      preparedStatement = connection.prepareStatement("SELECT * FROM test");
      rs = preparedStatement.executeQuery();
      if (rs.next()) {
        toReturn = rs.getString("city") + ", " + rs.getString("state");
      }
    } catch (java.sql.SQLException ex) {
    } finally {
      try {
        preparedStatement.close();
      } catch (java.sql.SQLException ex) {
        preparedStatement = null;
      }
      try {
        connection.close();
      } catch (java.sql.SQLException ex) {
        connection = null;
      }
      return toReturn;
    }
  }
}
