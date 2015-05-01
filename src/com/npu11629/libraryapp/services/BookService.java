package com.npu11629.libraryapp.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.npu11629.libraryapp.dao.jdbc.BookDB;
import com.npu11629.libraryapp.domain.Book;
import com.npu11629.libraryapp.domain.Member;

public class BookService {

	public static void addNewBook(Book book) {

		try {
			BookDB.insertNewBook(book);
			System.out.println("\nBook Added To The Database Successfully");
		} catch (Exception ex) {
			System.out.println("Failed because of exception: " + ex);
		}
	}

	public static void deleteBook(String title) throws SQLException,
			NamingException {
		BookDB.deleteByBookTitle(title);
		System.out.println("Record from Book table deleted successfully ");

	}

	public static int getTotalNumberOfBooks() throws SQLException,
			NamingException {
		int total = BookDB.getBookCount();
		System.out.println("Total number of books : " + total);
		return total;
	}

	public static Book findBookDetails(String title) throws SQLException,
			NamingException {
		Book book;
		book = BookDB.findBookByName(title);

		System.out.println("Details of the book for given name is : [ Title: "
				+ BookDB.findBookByName(title).getTitle() + ", Book ID: "
				+ BookDB.findBookByName(title).getBookid() + ", Author: "
				+ BookDB.findBookByName(title).getAuthor() + ", ISBN: "
				+ BookDB.findBookByName(title).getIsbn() + " ]");

		return book;

	}

	public static String findBookNameById(int bookid) throws SQLException,
			NamingException {
		String name = BookDB.findBookNameById(bookid);
		System.out.println("Title of the book with ID given is : "
				+ BookDB.findBookNameById(bookid));
		return name;
	}

	public static List<Member> getMemberList(String bookname)
			throws SQLException, NamingException {

		int bookid = BookDB.findBookIdByName(bookname);
		List<Member> memberList = BookIssueService.findMemberDetails(bookid);

		return memberList;
	}

	public static void updateRecord(Book book, int change) throws SQLException,
			NamingException {
		BookDB.updateIsbn(book, change);
		System.out.println("ISBN Record updated successfully ");

	}

	public static List<Book> getAllBooks() throws SQLException, NamingException {
		List<Book> list = new ArrayList<Book>();
		list = BookDB.findAllBooks();
		return list;
	}

}
