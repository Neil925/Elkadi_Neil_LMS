package lms;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class provides methods for interacting with the database to manage books in the library.
 * It includes setup, book retrieval, addition, removal, and status updating.
 *
 * @author Neil Elkadi
 * @version 1.0.0 11/17/2024
 */
public class Database {
    public static Connection connection;

    /**
     * Establishes a connection to the SQLite database using the provided database URL.
     * This method must be called before any database operations are performed.
     *
     * @return the success status of the database connection.
     */
    public static boolean setupDatabase() {
        String url = "jdbc:sqlite:library.db";
        try {
            connection = DriverManager.getConnection(url);
            return true;
        } catch (SQLException error) {
            System.out.println("There was an error with setting up the database.");
            System.out.println(error.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all books stored in the database.
     * Each book's details are read from the database and added to an ArrayList.
     *
     * @return an ArrayList containing all books found in the database.
     */
    public static ArrayList<Book> getBooks() {
        ArrayList<Book> bookList = new ArrayList<Book>();
        String sql = "SELECT * FROM books";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            var rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("barcode_id");
                String author = rs.getString("author");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                char status = rs.getString("status").charAt(0);

                int val = rs.getInt("due_date");
                Date dueDate;
                if (val == 0)
                    dueDate = null;
                else
                    dueDate = new Date(rs.getInt("due_date"));

                bookList.add(new Book(id, author, title, genre, status, dueDate));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (bookList.isEmpty())
                throw new Error("No books found.");
        }

        return bookList;
    }

    /**
     * Adds a new book to the database.
     * If the insertion is successful, it returns true; otherwise, false is returned.
     *
     * @param book the book to be added to the database.
     * @return true if the book was added successfully, false otherwise.
     */
    public static boolean addBook(Book book) {
        String sql = "INSERT INTO books(barcode_id, author, title, genre, status, due_date) VALUES(?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, book.getBarcodeId());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getTitle());
            pstmt.setString(4, book.getGenre());
            pstmt.setString(5, book.getStatus() + "");

            if (book.getDueDate() == null)
                pstmt.setNull(6, Types.INTEGER);
            else
                pstmt.setLong(6, book.getDueDate().getTime());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Removes a book with the specified title from the database.
     *
     * @param title the title of the book that should be removed.
     * @return true if the book was removed successfully, false otherwise.
     */
    public static boolean removeBook(String title) {
        String sql = "DELETE FROM books WHERE title = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Removes a book by its barcode_id from the database.
     *
     * @param id the barcode_id of the book to be removed.
     * @return true if the book was removed successfully, false otherwise.
     */
    public static boolean removeBook(int id) {
        String sql = "DELETE FROM books WHERE barcode_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Updates the status and due date of a book in the database.
     * If the book is checked out, the due date will be updated.
     *
     * @param book the book whose status and due date should be updated.
     * @return true if the update was successful, false otherwise.
     */
    public static boolean setStatus(Book book) {
        System.out.println(book.getStatus() + " " + book.getDueDate() + " " + book.getTitle());
        String sql = "UPDATE books SET status = ?, due_date = ? WHERE barcode_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getStatus() + "");
            if (book.getDueDate() == null)
                pstmt.setNull(2, Types.INTEGER);
            else
                pstmt.setLong(2, book.getDueDate().getTime());
            pstmt.setInt(3, book.getBarcodeId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
