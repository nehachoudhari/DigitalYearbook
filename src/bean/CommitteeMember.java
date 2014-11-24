package bean;

import exception.YearbookException;


/**
 * @author nhchdhr
 * 
 * This class represents the Committee Member entity and provides getters and setters for its fields
 * This entity is mapped to Committee_Member table in the database 
 *
 */

public class CommitteeMember extends Photograph{

	private long member_id;
	private String fName;
	private String lName;
	private String designation;
	private int deptId;
	
	public long getMember_id() {
		return member_id;
	}

	public void setMember_id(long member_id) {
		this.member_id = member_id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	
	public String add() throws YearbookException{
		String ret = "true";

		if(!ret.equalsIgnoreCase("true"))
			return "true";
		else 
			return "false";
	}
	public String modify() throws YearbookException{
		String ret = "true";

		if(!ret.equalsIgnoreCase("true"))
			return "true";
		else 
			return "false";
	}
	
	public String delete() throws YearbookException{
		String ret = "Success";

		if(!ret.equalsIgnoreCase("Success"))
			return "delete";
		else 
			return "false";
	}

}
