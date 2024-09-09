package lms;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/*
 * Neil Elkadi - CEN 3024C - 09/08/2024
 * Software Development 1
 * Entry point class. This program manages a library's collection of books.
 * It is a menu driven program that takes standard input for commands and reads new book entries from a file.
 */
public class Main {
  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    try {

      File importFile = new File(Book.importFile);

      if (!importFile.exists())
        new FileWriter(importFile).write("");
    } catch (Exception err) {
      System.err.println("Something went wrong checking for file: " + err);
    }

    menu();
  }

  /**
   * menu
   * A repeating function that ends when the user quits. This is where all other
   * functions get called from.
   */
  static void menu() {
    printMenuOptions();
    System.out.print("\nWhat action will you preform?: ");
    char input = scanner.nextLine().toLowerCase().charAt(0);

    switch (input) {
      case 'a':
        Book.addBook();
        System.out.println();
        break;
      case 'r':
        System.out.print("Enter the id for the book you'd like to remove: ");
        try {
          int id = scanner.nextInt();
          scanner.nextLine();
          Book.removeBook(id);
        } catch (Exception er) {
          System.err.println("Must be a number!");
        }
        break;
      case 'l':
        System.out.println();
        Book.printAll();
        System.out.println();
        break;
      case 'q':
        System.out.print("This action is irreversible and will wipe current library content. Are you sure? (y/N): ");
        if (scanner.nextLine().toLowerCase().charAt(0) == 'y') {
          System.out.println("Goodbye.");
          return;
        }
        break;
      default:
        System.err.println("Invalid input. Please try again.");
        break;
    }

    menu();
  }

  /**
   * printMenuOptions
   * Displays all options a user can provide during a menu prompt.
   */
  static void printMenuOptions() {
    System.out.println("[A]dd book from file");
    System.out.println("[R]emove book by id");
    System.out.println("[L]ist all stored books");
    System.out.println("[Q]uit");
  }
}
