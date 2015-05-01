package com.npu11629.libraryapp.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.npu11629.libraryapp.domain.BookIssue;

public class BookIssueDB {

	private static DataSource bookIssueDbDataSource = null;

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

		if (bookIssueDbDataSource == null) {
			bookIssueDbDataSource = initDataSource();
		}

		dbConn = bookIssueDbDataSource.getConnection();
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

	public static int findMemidByBookid(int bookid) throws SQLException,
			NamingException {
		int memid = 0;

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select memid from Bookissue where bookid='"
						+ bookid + "';";
				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					memid = rs.getInt("memid");
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return memid;
	}

	public static List<Integer> getMemidByBookid(int bookid)
			throws SQLException, NamingException {

		List<Integer> memids = new ArrayList<Integer>();

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select memid from Bookissue where bookid='"
						+ bookid + "';";

				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {

					int i = rs.getInt("memid"); 
					memids.add(i);
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return memids;
	}
	

	public static List<Integer> getBookidByMemid(int memid)
			throws SQLException, NamingException {

		List<Integer> bookid = new ArrayList<Integer>();

		System.out.println("step 1");

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select bookid from Bookissue where memid='"
						+ memid + "';";

				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					int i = rs.getInt("bookid"); 
					System.out.println("step 1");
					bookid.add(i);
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return bookid;
	}

	public static void insertEntry(BookIssue bookIssue) throws SQLException,
			NamingException {

		try (Connection dbConn = getConnection()) {
			// Turn off auto-commit so we can use transactions
			dbConn.setAutoCommit(false);

			try {
				String insertStmt = "INSERT INTO bookissue (memid, bookid, issuedate) VALUES (?,?,?)";
				//int id;
				int memid = bookIssue.getMemid();
				int bookid = bookIssue.getBookid();
				Date date = bookIssue.getDate();
				java.sql.Date timestamp = new java.sql.Date(date.getTime());

				try (PreparedStatement sqlStmt = dbConn.prepareStatement(
						insertStmt, Statement.RETURN_GENERATED_KEYS)) {
					//id = getGeneratedPrimaryKey(sqlStmt);
					sqlStmt.setInt(1, memid);
					sqlStmt.setInt(2, bookid);
					sqlStmt.setDate(3, timestamp);
					sqlStmt.executeUpdate();
				}
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
	}

	public static void deleteEntry(int memid, int bookid) throws SQLException,
			NamingException {

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "DELETE from bookissue where memid ='" + memid
						+ "' AND bookid ='" + bookid + "';";
				Statement sqlStmt = (Statement) dbConn.createStatement();
				sqlStmt.executeUpdate(sql);

				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
	}

}
