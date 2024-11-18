package lms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.Date;

/*
 * Neil Elkadi - CEN 3024C - 10/06/2024
 * Software Development 1
 * This class acts as a record class for Book information.
 */
public class Book {
  private int barcodeId;

  private String author;

  private String title;

  private String genre;

  private char status;

  private Date dueDate;

  public Book(int id, String author, String title, String genre) {
    setBarcodeId(id);
    setAuthor(author);
    setTitle(title);
    setGenre(genre);
    setStatus('A');
    setDueDate(null);
  }

  public Book(int id, String author, String title, String genre, char status, Date dueDate) {
    this(id, author, title, genre);
    setStatus(status);
    setDueDate(dueDate);
  }

  public int getBarcodeId() {
    return barcodeId;
  }

  public void setBarcodeId(int barcodeId) {
    this.barcodeId = barcodeId;
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
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public char getStatus() {
    return status;
  }

  public void setStatus(char status) {
    this.status = status;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public boolean isCheckedOut() {
    return dueDate != null;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  @Override
  public String toString() {
    String result = "" + this.barcodeId;
    result += " - " + this.title;
    result += " by " + this.author;
//    result += "\n\tGenre: " + this.genre;
//    result += "\n\tStatus: ";
//    if (this.status == 'C') {
//      result += "Checked out";
//      result += "\n\tDue: " + this.dueDate;
//    } else
//      result += "Available";

    return result;
  }
}
