package lms;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Neil Elkadi - CEN 3024C - 10/30/2024
 * Software Development 1
 * This class handles the user interaction for library operations, providing a GUI with options to add,
 * remove, check out, and check in books. LibraryManager makes calls to the Library class to perform
 * operations based on user input.
 */
public class LibraryManager extends JFrame {
    private JPanel contentPane;
    private JTextField bookIDTextField;
    private JTextField bookTitleTextField;
    private JButton searchID;
    private JButton searchTitle;
    private JCheckBox isCheckedOutBox;
    private JList bookList;
    private JLabel dueDateLabel;
    private JLabel authorLabel;
    private JLabel genreLabel;
    private JLabel warningLabel;
    private JButton checkInButton;
    private JButton checkOutButton;

    /**
     * LibraryManager Constructor
     * Initializes the GUI components, sets the JFrame properties, and attaches listeners to handle actions.
     */
    public LibraryManager() {
        bookList.setListData(Library.database.toArray());
        setTitle("Book Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        // Set the frame location to the center of the screen
        setLocationRelativeTo(null);

        searchID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBookId();
            }
        });

        searchTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFields(Library.getBook(bookTitleTextField.getText()));
            }
        });

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Library.checkinBook(bookTitleTextField.getText()))
                    JOptionPane.showMessageDialog(new JFrame(), "Could not check in.", "Warning", JOptionPane.ERROR_MESSAGE);
                setFields(Library.getBook(bookTitleTextField.getText()));
            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Library.checkoutBook(bookTitleTextField.getText()))
                    JOptionPane.showMessageDialog(new JFrame(), "Could not check out.", "Warning", JOptionPane.ERROR_MESSAGE);
                setFields(Library.getBook(bookTitleTextField.getText()));
            }
        });

        bookList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;

                setFields((Book) bookList.getSelectedValue());
            }
        });

        isCheckedOutBox.setEnabled(false);
        warningLabel.setVisible(false);

        setVisible(true);
    }

    /**
     * searchBookId
     * Searches for a book by ID from the text field input and displays its details in the GUI.
     */
    private void searchBookId() {
        try {
            int id = Integer.parseInt(bookIDTextField.getText());
            setFields(Library.getBook(id));
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    /**
     * setFields
     * Updates the GUI fields with the details of the given book, including title, author, genre, due date,
     * and check-out status.
     * @param book The book to display in the fields, or null if the book was not found.
     */
    private void setFields(Book book) {
        if (book == null) {
            warningLabel.setVisible(true);
            JOptionPane.showMessageDialog(new JFrame(), "No book found.", "Warning", JOptionPane.ERROR_MESSAGE);
            return;
        }

        warningLabel.setVisible(false);

        bookIDTextField.setText("" + book.getBarcodeId());
        bookTitleTextField.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        genreLabel.setText(book.getGenre());

        if (book.getDueDate() != null)
            dueDateLabel.setText(book.getDueDate().toString());
        else
            dueDateLabel.setText("");

        if (book.isCheckedOut() && !isCheckedOutBox.isSelected() || isCheckedOutBox.isSelected() && !book.isCheckedOut()) {
            isCheckedOutBox.setEnabled(true);
            isCheckedOutBox.doClick();
            isCheckedOutBox.setEnabled(false);
        }
    }

//    private void setBook(String title) {
//        Library.
//    }
}
