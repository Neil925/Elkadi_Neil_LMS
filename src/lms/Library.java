package lms;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/*
 * Neil Elkadi - CEN 3024C - 10/06/2024
 * Software Development 1
 * This class acts as a record class for Book information.
 */
public class Library {
  public static ArrayList<Book> database = new ArrayList<Book>();
  public static String importFile;

  /**
   * printAll
   * Prints all book records that are held in the {@code database}.
   */
  public static void printAll() {
    if (database.isEmpty()) {
      System.out.println("No books present.");
      return;
    }

    System.out.println("Book list:");

    for (Book book : database)
      System.out.println(book);
  }

  /**
   * addBook
   * Adds all books from the file set by the user to the {@code database} array
   * list.
   */
  public static void addBook() {
    try {
      File file = new File(importFile);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        if (line.startsWith("#"))
          continue;

        // Must include 6 fields: barcodeId, author, title, genre, status, due date
        if (!line.matches(".*,.*,.*,.*"))
          continue;

        String[] items = line.split(",");

        int id = Integer.parseInt(items[0].trim());
        String author = items[1].trim();
        String title = items[2].trim();
        String genre = items[3].trim();

        if (!addBook(id, author, title, genre)) {
          System.err.println("A book with the id " + id + " already exists!");
          continue;
        }
      }

      System.out.println("All actions complete.");
    } catch (Exception e) {
      System.err.println(
          "Something went wrong when adding books. Please make sure " + importFile + " is formatted coorectly.");
      System.err.println(e);
    }
  }

  /**
   * addBook
   * Adds a new book entry to the library database if the barcode ID does not already exist.
   * @param id The unique barcode ID for the book.
   * @param author The author's name.
   * @param title The title of the book.
   * @param genre The genre or category of the book.
   * @return true if the book was added successfully, false if the book ID already exists.
   */
  public static boolean addBook(int id, String author, String title, String genre) {
    Book book = new Book(id, author, title, genre);

    if (database.stream().anyMatch(x -> x.getBarcodeId() == book.getBarcodeId())) {
      System.err.println("A book with the id " + book.getBarcodeId() + " already exists!");
      return false;
    }

    database.add(book);
    System.out.println("Added " + book);

    return true;
  }

  /**
   * getBook
   * Retrieves a book from {@code database} by title.
   * @param title The title field of the book to be retrieved.
   * @return The book if found, or null otherwise.
   */
  public static Book getBook(String title) {
    for (Book value : database) {
      if (Objects.equals(value.getTitle(), title)) {
        return value;
      }
    }

    return null;
  }

  /**
   * getBook
   * Retrieves a book from {@code database} by id.
   * @param id The id field of the book to be retrieved.
   * @return The book if found, or null otherwise.
   */
  public static Book getBook(int id) {
    for (Book value : database) {
      if (value.getBarcodeId() == id) {
        return value;
      }
    }

    return null;
  }

  /**
   * removeBook
   * Removes a book from {@code database} by id.
   *
   * @param id The id field of the book to be removed.
   */
  public static void removeBook(int id) {
    Book book = null;
      for (Book value : database) {
          if (value.getBarcodeId() == id) {
              book = value;
              break;
          }
      }

    if (book == null) {
      System.err.println("No book with matching id " + id + " was found. Nothing removed.");
      return;
    }

    System.out.println("Removing " + book.getTitle() + " by " + book.getAuthor());
    database.remove(book);
  }

  /**
   * removeBook
   * Overload that Removes a book from {@code database} by title.
   * 
   * @param title The title field of the book to be removed.
   */
  public static void removeBook(String title) {
    Book book = null;
      for (Book value : database) {
          if (value.getTitle().equals(title)) {
              book = value;
              break;
          }
      }

    if (book == null) {
      System.err.println("No book with matching title" + title + " was found. Nothing removed.");
      return;
    }

    System.out.println("Removing " + book.getTitle() + " by " + book.getAuthor());
    database.remove(book);
  }

  /**
   * checkoutBook
   * Marks a book as being checked out in {@code database} by title.
   * 
   * @param title The title field of the book to be checked out.
   */
  public static boolean checkoutBook(String title) {
    for (Book book : database) {
      if (!book.getTitle().equals(title))
        continue;

      if (book.getStatus() == 'C') {
        System.out.println("Book is already checked out!");
        return false;
      }

      book.setStatus('C');
      book.setDueDate(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)));
      System.out.println("Book checked out successfully.");
      return true;
    }

    System.out.println("No book with title \"" + title + "\" was found.");
    return false;
  }

  /**
   * checkinBook
   * Marks a book as being checked in for the {@code database} array by title.
   * 
   * @param title The title field of the book to be checked in.
   */
  public static boolean checkinBook(String title) {
    for (Book book : database) {
      if (!book.getTitle().equals(title))
        continue;

      if (book.getStatus() == 'A') {
        System.out.println("Book is already checked in!");
        return false;
      }

      book.setStatus('A');
      book.setDueDate(null);
      System.out.println("Book checked in successfully.");
      return true;
    }

    System.out.println("No book with title \"" + title + "\" was found.");
    return false;
  }
}
