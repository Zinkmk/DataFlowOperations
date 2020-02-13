package dataEngineering;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {

  /**
   * Saves an author to the DB from AuthorParser
   * @param author, the object to be saved to the database
   * @return True if ir worked, false if not.
   */
  public static boolean saveAuthor(AuthorParser author) {
    Connection connection = null;
    PreparedStatement statement = null;
    boolean success = false;
    try {
      if ((connection = getConnection()) != null) {
        statement =
            connection.prepareStatement(
                "INSERT INTO author (author_name, author_email, author_url) VALUES (?,?,?)");
        statement.setString(1, author.getName());

        if (author.getEmail().isBlank() || author.getEmail().isEmpty()) {
          statement.setString(2, author.getName() + " No email associated with this name");
        } else {
          statement.setString(2, author.getEmail());
        }
        if (author.getUrl().isBlank() || author.getUrl().isEmpty()) {
          statement.setString(3, author.getName() + " No URL associated with this name");
        } else {
          statement.setString(3, author.getUrl());
        }
        statement.executeUpdate();
        success = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        System.out.println("Database did not close correctly");
      }
    }
    return success;
  }

  /**
   * Saves books from CSV lines
   * @param line, the line that the CSV details are read from.
   * @return same as before, T = success F = error
   */
  public static boolean saveCSV(String[] line) {
    Connection connection = null;
    PreparedStatement statement = null;
    boolean success = false;
    try {
      if ((connection = getConnection()) != null) {
        //save book details
        statement = connection.prepareStatement(
            "INSERT OR IGNORE INTO book (isbn, publisher_name, author_name, book_title)"
                + "Values (?,?,?,?)");
        //rows are {isbn, title, author, publisher, store, location
        statement.setString(1, line[0]); //ISBN
        statement.setString(2, line[3]); //Author
        statement.setString(3, line[2]); //Publisher
        statement.setString(4, line[1]); //Title
        statement.executeUpdate();
        success = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        System.out.println("error closing database connections");
      }
    }
    return success;
  }

  /**
   * Opens a connection to the database.
   * @return the connection
   */
  private static Connection getConnection() {
    Connection connection = null;
    if (Files.exists(Paths.get("src/main/Data/bookstore.db"))) { //verifying database
      try {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/Data/bookstore.db");
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }
}



