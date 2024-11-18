package lms;

/*
 * Neil Elkadi - CEN 3024C - 10/06/2024
 * Software Development 1
 * Entry point class. This program manages a library's collection of books.
 * It is a GUI driven program that takes graphical input to view and adjust books in a library.
 */
public class Main {
    public static void main(String[] args) {
        Database.setupDatabase();
//        Library.addBooks();
        new LibraryManager();
    }
}
