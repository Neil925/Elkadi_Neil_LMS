package lms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * Neil Elkadi - CEN 3024C - 09/08/2024
 * Software Development 1
 * This class acts as a record class for Book information as well as a container for all book and books array related actions.
 */
public class Book {
  private static ArrayList<Book> books = new ArrayList<Book>();
  public static final String importFile = "./import_file.txt";

  /**
   * printAll
   * Prints all book records that are held in the {@code books}.
   */
  public static void printAll() {
    if (books.size() == 0) {
      System.out.println("No books present.");
      return;
    }

    System.out.println("Book list:");

    for (Book book : books)
      System.out.println(book);
  }

  /**
   * addBook
   * Adds all books from CWD/import_file.txt to the {@code books} array list.
   */
  public static void addBook() {
    try {
      File file = new File(importFile);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        if (line.startsWith("#"))
          continue;

        if (!line.matches(".*,.*,.*"))
          continue;

        String items[] = line.split(",");

        int id = Integer.parseInt(items[0].trim());
        String title = items[1].trim();
        String author = items[2].trim();

        Book book = new Book(id, author, title);

        if (books.stream().anyMatch(x -> x.id == book.id)) {
          System.err.println("A book with the id " + book.id + " already exists!");
          continue;
        }

        books.add(book);

        System.out.println("Added " + book);
      }

      System.out.println("All actions complete.");
    } catch (Exception e) {
      System.err.println(
          "Something went wrong when adding books. Please make sure " + importFile + " is formatted coorectly.");
      System.err.println(e);
    }
  }

  /**
   * removeBook
   * Removes a book from {@code books} by id.
   * 
   * @param id The id field of the book to be removed.
   */
  public static void removeBook(int id) {
    Book book = null;
    for (int i = 0; i < books.size(); i++) {
      if (books.get(i).id == id) {
        book = books.get(i);
        break;
      }
    }

    if (book == null) {
      System.err.println("No book with matching id " + id + " was found. Nothing removed.");
      return;
    }

    System.out.println("Removing " + book.title + " by " + book.author);
    books.remove(book);
  }

  private int id;

  private String author;

  private String title;

  public Book(int id, String author, String title) {
    setId(id);
    setAuthor(author);
    setTitle(title);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  };

  public String toString() {
    return this.id + ": " + this.title + " by " + this.author;
  }
}
