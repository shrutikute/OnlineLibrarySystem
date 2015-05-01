package com.npu11629.libraryapp.services;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.npu11629.libraryapp.dao.jdbc.MemberDB;
import com.npu11629.libraryapp.domain.Member;

public class MemberService {

	public static void addNewMember(Member member) {

		try {
			MemberDB.insertMember(member);
			System.out.println("Member Added To The Database Successfully");
		} catch (Exception ex) {
			System.out.println("Failed because of exception: " + ex);
		}
	}

	public static void deleteByMemberId(int id) throws SQLException,
			NamingException {
		MemberDB.deleteByMemberId(id);
		System.out.println("Record from Member table deleted successfully \n");
	}

	public static int getTotalNumberOfMembers() throws SQLException,
			NamingException {
		int total = MemberDB.getMemberCount();
		System.out.println("Total number of Members : " + total);
		return total;
	}

	public static Member findMemberDetails(String title) throws SQLException,
			NamingException {
		Member member;
		member = MemberDB.findMemberByName(title);

		System.out.println("Details of the Member for given name is : [ Name: "
				+ MemberDB.findMemberByName(title).getName() + ", Member ID: "
				+ MemberDB.findMemberByName(title).getMemid()
				+ ", Member Address: "
				+ MemberDB.findMemberByName(title).getAddress()
				+ ", Member Classification: "
				+ MemberDB.findMemberByName(title).getClassification() + " ]");

		return member;

	}

	public static String findMemberNameById(int memid) throws SQLException,
			NamingException {
		String name = MemberDB.findMemberNameById(memid);
		System.out.println("Name of the Member with given Id is : "
				+ MemberDB.findMemberNameById(memid));
		return name;
	}

	public static Member findMemberIdByName(String name) throws SQLException,
			NamingException {

		Member mem = MemberDB.findMemberIdByName(name);
		System.out.println("ID of the Member with given name is : " + mem.getMemid());
		return mem;
	}

	public static boolean validate(String user, String pass)
			throws NamingException {
		return MemberDB.validate(user, pass);
	}

}
