package com.npu11629.libraryapp.domain;

import java.util.ArrayList;

public class Member {
	private int memid;
	private ArrayList<Member> memberList;
	private String name;
	private String address;
	private String classification;
	private String userName;
	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getMemid() {
		return memid;
	}

	public void setMemid(int memid) {
		this.memid = memid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public ArrayList<Member> getMemberList() {
		return memberList;
	}

	public void setStudentList(ArrayList<Member> memberList) {
		this.memberList = memberList;
	}

	public void addStudent(Member member) {
		memberList.add(member);
	}

}
