package lms;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * Contains core library logic. Includes book removals, additions, check out, check in, and getting book.
 *
 * @author Neil Elkadi
 * @version 1.0.0 11/19/2024
 */
public class Library {
    /**
     * Prints all book records that are held in the database.
     */
    public static void printAll() {
        var database = Database.getBooks();

        if (database.isEmpty()) {
            System.out.println("No books present.");
            return;
        }

        System.out.println("Book list:");

        for (Book book : database)
            System.out.println(book);
    }

    /**
     * Adds a random set of books for testing.
     */
    public static void addBooks() {
        Database.addBook(new Book(1001, "J.K. Rowling", "Harry Potter and the Sorcerer's Stone", "Fantasy", 'C', new Date(1731899260538L)));
        Database.addBook(new Book(1002, "George Orwell", "1984", "Dystopian", 'A', null));
        Database.addBook(new Book(1003, "Jane Austen", "Pride and Prejudice", "Romance", 'A', null));
        Database.addBook(new Book(1004, "Mark Twain", "The Adventures of Huckleberry Finn", "Adventure", 'C', new Date(1672522560538L)));
        Database.addBook(new Book(1005, "F. Scott Fitzgerald", "The Great Gatsby", "Classic", 'C', new Date(1604259260538L)));
        Database.addBook(new Book(1006, "Harper Lee", "To Kill a Mockingbird", "Historical Fiction", 'A', null));
        Database.addBook(new Book(1007, "J.R.R. Tolkien", "The Hobbit", "Fantasy", 'A', null));
        Database.addBook(new Book(1008, "Charles Dickens", "A Tale of Two Cities", "Historical Fiction", 'C', new Date(1688004960538L)));
        Database.addBook(new Book(1009, "Ernest Hemingway", "The Old Man and the Sea", "Literary Fiction", 'A', null));
        Database.addBook(new Book(1010, "Haruki Murakami", "Norwegian Wood", "Contemporary Fiction", 'C', new Date(1685672960538L)));
        Database.addBook(new Book(1011, "Leo Tolstoy", "War and Peace", "Historical Fiction", 'A', null));
        Database.addBook(new Book(1012, "J.D. Salinger", "The Catcher in the Rye", "Literary Fiction", 'C', new Date(1672535660538L)));
        Database.addBook(new Book(1013, "George R.R. Martin", "A Game of Thrones", "Fantasy", 'A', null));
        Database.addBook(new Book(1014, "William Shakespeare", "Hamlet", "Tragedy", 'C', new Date(1661899260538L)));
        Database.addBook(new Book(1015, "Agatha Christie", "Murder on the Orient Express", "Mystery", 'A', null));
        Database.addBook(new Book(1016, "J.R.R. Tolkien", "The Lord of the Rings", "Fantasy", 'A', null));
        Database.addBook(new Book(1017, "Isaac Asimov", "Foundation", "Science Fiction", 'C', new Date(1710507960538L)));
        Database.addBook(new Book(1018, "F. Scott Fitzgerald", "Tender Is the Night", "Classic", 'C', new Date(1649402160538L)));
        Database.addBook(new Book(1019, "Margaret Atwood", "The Handmaid's Tale", "Dystopian", 'A', null));
        Database.addBook(new Book(1020, "Kurt Vonnegut", "Slaughterhouse-Five", "Science Fiction", 'A', null));

        System.out.println("All actions complete.");
    }

    /**
     * Adds a new book entry to the library database if the barcode ID does not already exist.
     *
     * @param id     The unique barcode ID for the book.
     * @param author The author's name.
     * @param title  The title of the book.
     * @param genre  The genre or category of the book.
     * @return true if the book was added successfully, false if the book ID already exists.
     */
    public static boolean addBook(int id, String author, String title, String genre) {
        var database = Database.getBooks();

        Book book = new Book(id, author, title, genre);

        if (database.stream().anyMatch(x -> x.getBarcodeId() == book.getBarcodeId())) {
            System.err.println("A book with the id " + book.getBarcodeId() + " already exists!");
            return false;
        }

        Database.addBook(book);
        System.out.println("Added " + book);

        return true;
    }

    /**
     * Retrieves a book from the database by title.
     *
     * @param title The title field of the book to be retrieved.
     * @return The book if found, or null otherwise.
     */
    public static Book getBook(String title) {
        var database = Database.getBooks();

        for (Book value : database) {
            if (Objects.equals(value.getTitle(), title)) {
                return value;
            }
        }

        return null;
    }

    /**
     * Retrieves a book from database by id.
     *
     * @param id The id field of the book to be retrieved.
     * @return The book if found, or null otherwise.
     */
    public static Book getBook(int id) {
        var database = Database.getBooks();

        for (Book value : database) {
            if (value.getBarcodeId() == id) {
                return value;
            }
        }

        return null;
    }

    /**
     * Removes a book from database by id.
     *
     * @param id The id field of the book to be removed.
     */
    public static void removeBook(int id) {
        var database = Database.getBooks();

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

        Database.removeBook(book.getBarcodeId());
        System.out.println("Removed " + book.getTitle() + " by " + book.getAuthor());
    }

    /**
     * Overload that Removes a book from database by title.
     *
     * @param title The title field of the book to be removed.
     */
    public static void removeBook(String title) {
        var database = Database.getBooks();

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

        Database.removeBook(book.getBarcodeId());
        System.out.println("Removed " + book.getTitle() + " by " + book.getAuthor());
    }

    /**
     * Marks a book as being checked out in database by title.
     *
     * @param title The title field of the book to be checked out.
     */
    public static boolean toggleStatus(String title) {
        var database = Database.getBooks();

        for (Book book : database) {
            if (!book.getTitle().equals(title))
                continue;

            if (book.isCheckedOut()) {
                book.setStatus('A');
                book.setDueDate(null);
            } else {
                book.setStatus('C');
                book.setDueDate(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)));
            }

            if (Database.setStatus(book)) {
                System.out.println("Book checked " + (book.isCheckedOut() ? "out" : "in") + " successfully.");
                return true;
            } else {
                System.out.println("Book checked " + (book.isCheckedOut() ? "out" : "in") + " failed.");
                return false;
            }
        }

        System.out.println("No book with title \"" + title + "\" was found.");
        return false;
    }

    /**
     * Marks a book as being checked in for the database array by title.
     *
     * @param title The title field of the book to be checked in.
     */
    public static boolean checkinBook(String title) {
        var database = Database.getBooks();

        for (Book book : database) {
            if (!book.getTitle().equals(title))
                continue;

            if (book.getStatus() == 'A') {
                System.out.println("Book is already checked in!");
                return false;
            }

            book.setStatus('A');
            book.setDueDate(null);

            Database.setStatus(book);

            System.out.println("Book checked in successfully.");
            return true;
        }

        System.out.println("No book with title \"" + title + "\" was found.");
        return false;
    }
}
