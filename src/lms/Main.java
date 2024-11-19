package lms;

/**
 * Entry point class. This program manages a library's collection of books.
 * It is a GUI driven program that takes graphical input to view and adjust books in a library.
 *
 * @author Neil Elkadi - CEN 3024C - 10/06/2024
 * @version 1.0.0 11/19/24
 */
public class Main {

    /**
     *Entry point function. Initializes database, populates books, then runs the {@link lms.LibraryManager} GUI.
     *
     * @param args Command line arguments passed into the program. Unused by this program.
     */
    public static void main(String[] args) {
        if (!Database.setupDatabase())
            return;
        Library.addBooks();
        new LibraryManager();
    }
}
