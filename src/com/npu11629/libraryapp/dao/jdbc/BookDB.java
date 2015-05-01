package com.npu11629.libraryapp.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.npu11629.libraryapp.domain.Book;

public class BookDB {

	private static DataSource bookDbDataSource = null;

	public static DataSource initDataSource() throws NamingException {
		Context initContext;
		
		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource dataSource = (DataSource) envContext
					.lookup("jdbc/librarydb");
			return dataSource;
		} finally {
		}
	}

	public static Connection getConnection() throws NamingException,
			SQLException {
		Connection dbConn;

		if (bookDbDataSource == null) {
			bookDbDataSource = initDataSource();
		}

		dbConn = bookDbDataSource.getConnection();
		return dbConn;
	}

	public static int getGeneratedPrimaryKey(PreparedStatement sqlStmt)
			throws SQLException {
		int id = -1;

		try (ResultSet generatedKeys = sqlStmt.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				id = generatedKeys.getInt(1);
			}
		}
		return id;
	}

	public static int getBookCount() throws SQLException, NamingException {
		int count = 0;

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String selectStmt = "select count(*) AS total from Book";
				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(selectStmt);
				while (rs.next()) {
					count = rs.getInt("total");
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return count;
	}

	public static void insertNewBook(Book newBook) throws SQLException,
			NamingException {

		try (Connection dbConn = getConnection()) {
			// Turn off auto-commit so we can use transactions
			dbConn.setAutoCommit(false);

			try {
				String insertStmt = "INSERT INTO book (title, author, isbn) VALUES (?,?,?)";
				String title = newBook.getTitle();
				String author = newBook.getAuthor();
				int isbn = newBook.getIsbn();

				try (PreparedStatement sqlStmt = dbConn.prepareStatement(
						insertStmt, Statement.RETURN_GENERATED_KEYS)) {
					sqlStmt.setString(1, title);
					sqlStmt.setString(2, author);
					sqlStmt.setInt(3, isbn);
					sqlStmt.executeUpdate();

				}
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
	}

	public static String findBookNameById(int bookid) throws SQLException,
			NamingException {

		String name = null;
		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select title from Book where bookid=" + bookid
						+ ";";
				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					name = rs.getString("title");
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return name;
	}

	public static Book findBookByName(String title) throws SQLException,
			NamingException {

		Book b1 = new Book();

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select * from Book where title='" + title + "';";
				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);

				int bookID = rs.getInt(0);
				String bookTitle = rs.getString(1);
				String author = rs.getString(2);
				int isbn = rs.getInt(3);

				b1.setBookid(bookID);
				b1.setTitle(bookTitle);
				b1.setAuthor(author);
				b1.setIsbn(isbn);

				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return b1;
	}

	public static Book findBookById(int bookid) throws SQLException,
			NamingException {

		Book b1 = new Book();

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select bookid, title from Book where bookid='"
						+ bookid + "';";

				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					int bookID = rs.getInt("bookid");
					String bookTitle = rs.getString("title");
					b1.setBookid(bookID);
					b1.setTitle(bookTitle);
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return b1;
	}

	public static int findIdByName(String title) throws SQLException,
			NamingException {

		int id = 0;
		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select bookid from Book where title='" + title
						+ "';";
				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					id = rs.getInt("bookid");
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return id;
	}

	public static void deleteByBookTitle(String title) throws SQLException,
			NamingException {

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "DELETE from book where title ='" + title + "';";
				Statement sqlStmt = dbConn.createStatement();
				sqlStmt.executeUpdate(sql);

				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
	}

	public static void updateIsbn(Book book, int change) throws SQLException,
			NamingException {
		String bookName;
		int bookid;
		int curIsbn, newIsbn;

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);

			String sql = "update book set isbn=? where bookid=?";
			try (PreparedStatement updatePrepStmt = dbConn
					.prepareStatement(sql)) {

				bookid = book.getBookid();
				bookName = book.getTitle();

				curIsbn = findIsbnByName(bookName);
				newIsbn = curIsbn + change;

				updatePrepStmt.setInt(1, newIsbn);
				updatePrepStmt.setInt(2, bookid);

				updatePrepStmt.executeQuery();

				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
	}

	public static int findBookIdByName(String title) throws SQLException,
			NamingException {
		int id = 0;
		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select bookid from Book where title='" + title
						+ "';";

				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					id = rs.getInt("bookid");
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return id;
	}

	public static int findIsbnByName(String title) throws SQLException,
			NamingException {
		int isbn = 0;
		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select isbn from Book where title='" + title
						+ "';";
				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					isbn = rs.getInt("isbn");
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return isbn;
	}

	public static List<Book> findAllBooks() throws SQLException,
			NamingException {

		List<Book> book1 = new ArrayList<Book>();

		// Book book = new Book();

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select title, bookid from book";

				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					Book book = new Book();
					book.setTitle(rs.getString("title"));
					book.setBookid(rs.getInt("bookid"));
					book1.add(book);
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return book1;
	}

}
