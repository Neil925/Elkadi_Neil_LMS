package lms;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Main
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

  static void printMenuOptions() {
    System.out.println("[A]dd book from file");
    System.out.println("[R]emove book by id");
    System.out.println("[L]ist all stored books");
    System.out.println("[Q]uit");
  }
}
