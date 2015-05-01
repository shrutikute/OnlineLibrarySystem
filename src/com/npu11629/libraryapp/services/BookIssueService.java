package com.npu11629.libraryapp.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.npu11629.libraryapp.dao.jdbc.BookDB;
import com.npu11629.libraryapp.dao.jdbc.BookIssueDB;
import com.npu11629.libraryapp.dao.jdbc.MemberDB;
import com.npu11629.libraryapp.domain.Book;
import com.npu11629.libraryapp.domain.BookIssue;
import com.npu11629.libraryapp.domain.Member;

public class BookIssueService {

	public static List<Member> findMemberDetails(int bookid)
			throws SQLException, NamingException {

		List<Integer> memids = new ArrayList<Integer>();
		List<Member> members = new ArrayList<Member>();

		memids = BookIssueDB.getMemidByBookid(bookid);
		int length = memids.size();

		for (int i = 0; i < length; i++) {

			Member mem = new Member();
			int mem_id = memids.get(i);
			mem = MemberDB.findMemberById(mem_id);
			members.add(mem);
		}
		return members;
	}

	public static List<Book> findBookDetails(int memid) throws SQLException,
			NamingException {

		List<Integer> bookids = new ArrayList<Integer>();
		List<Book> books = new ArrayList<Book>();

		bookids = BookIssueDB.getBookidByMemid(memid);
		int length = bookids.size();

		for (int i = 0; i < length; i++) {

			Book book = new Book();
			int book_id = bookids.get(i);
			book = BookDB.findBookById(book_id);
			books.add(book);
		}
		return books;
	}

	public static void addNewEntry(BookIssue bookIssue) {

		try {
			BookIssueDB.insertEntry(bookIssue);
			System.out
					.println("Transaction Added To The Database Successfully");
		} catch (Exception ex) {
			System.out.println("Failed because of exception: " + ex);
		}
	}

	public static void deleteEntry(int memid, int bookid) {

		try {
			BookIssueDB.deleteEntry(memid, bookid);
			System.out
					.println("Transaction Added To The Database Successfully");
		} catch (Exception ex) {
			System.out.println("Failed because of exception: " + ex);
		}
	}
}
