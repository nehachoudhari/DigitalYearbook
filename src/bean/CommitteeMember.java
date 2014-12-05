package bean;

import helper.DropboxUploaderHelper;

import javax.ejb.EJB;

import service.CommitteeMemberService;
import exception.YearbookException;


/**
 * @author nhchdhr
 * 
 * This class represents the Committee Member entity and provides getters and setters for its fields
 * This entity is mapped to Committee_Member table in the database 
 *
 */

public class CommitteeMember extends Photograph{

	@EJB
	CommitteeMemberService memberService;
	
	@EJB
	Student studentBean;
	
	private long memberId;
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	private String fName;
	private String lName;
	private String designation;
	private int deptId;
	private String photoUrl;
	
	private String selectedComId;
	
	public String getSelectedComId() {
		return selectedComId;
	}
	public void setSelectedComId(String selectedComId) {
		this.selectedComId = selectedComId;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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
		try{
			System.out.println("Inside add");
			System.out.println("Inside add 1 - "+this.file);
			System.out.println(this.file.getFileName());
			copyFile(this.file.getFileName(), this.file.getInputstream(), "Committee");
	
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			String dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Committee");
			System.out.println("Dropbox "+dropboxUrl);
			System.out.println(studentBean);
			boolean ret = memberService.addMember(fName,lName,designation,studentBean.getDeptId(), dropboxUrl);
			
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
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

	public boolean equals(Object other)
	{
	    return other instanceof CommitteeMember && (selectedComId != null) ? selectedComId.equals(((CommitteeMember) other).getMemberId()) : (other == this);
	}

	public int hashCode()
	{
	    return selectedComId != null ? this.getClass().hashCode() + selectedComId.hashCode() : super.hashCode();
	}
}
