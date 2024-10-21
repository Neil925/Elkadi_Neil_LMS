package testing;

import lms.Book;
import lms.Library;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Add Book Test")
    void addBook() throws IOException {

        Library.addBook(1001, "F. Scott Fitzgerald", "The Great Gatsby", "Action");
        Library.addBook(1002, "George Orwell", "1984", "Horror");
        Library.addBook(1003, "Harper Lee", "To Kill a Mockingbird", "Drama");
        Library.addBook(1004, "Herman Melville", "Moby-Dick", "Historical");
        Library.addBook(1005, "Jane Austen", "Pride and Prejudice", "Adventure");
        Library.addBook(1006, "J.D. Salinger", "The Catcher in the Rye", "Biography");
        Library.addBook(1007, "Leo Tolstoy", "War and Peace", "Drama");
        Library.addBook(1008, "J.R.R. Tolkien", "The Hobbit", "Horror");
        Library.addBook(1009, "Fyodor Dostoevsky", "Crime and Punishment", "Action");
        Library.addBook(1010, "James Joyce", "Ulysses", "Family");

        Assertions.assertEquals(10, Library.database.size(), "Error: Array size is wrong!");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Remove Book ID Test")
    void removeBook() {
        Library.removeBook(1010);

        Assertions.assertEquals(9, Library.database.size(), "Error: Array size is wrong!");

        for (Book book : Library.database) {
            Assertions.assertNotEquals(book.getBarcodeId(), 1010, "Error: Book should have been removed!");
        }

        Library.addBook(1010, "James Joyce", "Ulysses", "Family");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Remove Book Title Test")
    void testRemoveBook() {
        Library.removeBook("Crime and Punishment");

        Assertions.assertEquals(9, Library.database.size(), "Error: Array size is wrong!");

        for (Book book : Library.database) {
            Assertions.assertNotEquals(book.getTitle(), "Crime and Punishment", "Error: Book should have been removed!");
        }

        Library.addBook(1009, "Fyodor Dostoevsky", "Crime and Punishment", "Action");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Checkout Book Test")
    void checkoutBook() {
        Library.checkoutBook("The Great Gatsby");

        Date due = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7));
        long val = Library.database.getFirst().getDueDate().getTime() - due.getTime();

        Assertions.assertTrue(val < 1000 && val > -1000 , "Error: Read / Write is too slow or failed to assign date!");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Check In Book Test")
    void checkinBook() {
        Library.checkinBook("The Great Gatsby");

        Assertions.assertNull(Library.database.getFirst().getDueDate(), "Error: There should no longer be a due date!");
    }
}