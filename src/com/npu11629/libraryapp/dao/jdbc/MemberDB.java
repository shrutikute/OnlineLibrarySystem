package com.npu11629.libraryapp.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.npu11629.libraryapp.domain.Member;


public class MemberDB  {
	
	private static DataSource memberDbDataSource=null;

	public static DataSource initDataSource() throws NamingException {
    	Context initContext;
    	
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    	DataSource dataSource = (DataSource)envContext.lookup("jdbc/librarydb");
	    	return dataSource;
		} finally {
		}
	}

	
	public static Connection getConnection() throws NamingException, SQLException {
    	Connection dbConn;
    	
    	if (memberDbDataSource==null) {
    		memberDbDataSource = initDataSource();
    	}
    	
    	dbConn = memberDbDataSource.getConnection();
    	return dbConn;
    }
    
    
	public static int getMemberCount() throws SQLException, NamingException {
		int count = 0;

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String selectStmt = "select count(*) AS total from Member";
				Statement sqlStmt = (Statement) dbConn.createStatement();
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
	
	
	
	public static void insertMember(Member member) throws SQLException, NamingException {
		

		try (Connection dbConn = getConnection()) {
			// Turn off auto-commit so we can use transactions
			dbConn.setAutoCommit(false);

			try {
				String insertStmt = "INSERT INTO member (name, address, classification, username, password) VALUES (?,?,?,?,?)";
				String name = member.getName();
				String address = member.getAddress();
				String classification = member.getClassification();
				String username = member.getUserName();
				String password = member.getPassword();

				try (PreparedStatement sqlStmt = dbConn.prepareStatement(
						insertStmt)) {
					sqlStmt.setString(1, name);
					sqlStmt.setString(2, address);
					sqlStmt.setString(3, classification);
					sqlStmt.setString(4, username);
					sqlStmt.setString(5, password);
					sqlStmt.executeUpdate();

				}
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
	}
	
	

	public static String findMemberNameById(int memid) throws SQLException, NamingException {
		String name = null;
		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select name from member where memid=" + memid
						+ ";";
				Statement sqlStmt = (Statement) dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					name = rs.getString("name");
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

	
	public static Member findMemberIdByName(String username) throws SQLException, NamingException {
		
		int id = 0;
		Member mem = new Member();

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select memid, name from member where username='" + username
						+ "';";
				
				Statement sqlStmt = dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					id = rs.getInt("memid");
					String name = rs.getString("name");
					
					mem.setName(name);
					mem.setMemid(id);
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
				
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return mem;
	}
	
	
	public static Member findMemberByName(String memName) throws SQLException, NamingException {
		
		Member mem1 = new Member();

		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select * from Member where name='" + memName + "';";
				Statement sqlStmt = (Statement) dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);

				int memid = rs.getInt(0);
				String name = rs.getString(1);
				String address = rs.getString(2);
				String classification = rs.getString(3);
				String username = rs.getString(4);
				String password = rs.getString(5);

				mem1.setMemid(memid);
				mem1.setName(name);
				mem1.setAddress(address);
				mem1.setClassification(classification);
				mem1.setUserName(username);
				mem1.setPassword(password);

				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return mem1;
	}

	
	
	public static Member findMemberById(int memid) throws SQLException, NamingException {
		
		Member mem1 = new Member();
				
		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "select memid, name from member where memid ='"+memid+"';";
				
				Statement sqlStmt = (Statement) dbConn.createStatement();
				ResultSet rs = sqlStmt.executeQuery(sql);
				while (rs.next()) {
					int id = rs.getInt("memid");
					String name = rs.getString("name");

					mem1.setMemid(id);
					mem1.setName(name);
				}
				rs.close();
				sqlStmt.close();
				dbConn.commit();
			} catch (Exception ex) {
				dbConn.rollback();
				throw ex;
			}
		}
		return mem1;
	}
	
	
	public static void deleteByMemberId(int memid) throws SQLException, NamingException {
		
		try (Connection dbConn = getConnection()) {
			dbConn.setAutoCommit(false);
			try {
				String sql = "DELETE from member where memid ='" + memid + "';";
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
		
	
	public static boolean validate(String user, String pass) throws NamingException
	{
		String SQL = "select * from member where username = '"+user+"' and password = '"+pass+"';";
		System.out.println("SQL : "+ SQL);
		Statement stmt;
		try(Connection dbConn = getConnection()) 
		{
			stmt = dbConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			if (null!= rs && rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception : "+e.getMessage());
			return false;
		}
	}	
}


