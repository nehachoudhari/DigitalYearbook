package bean;
import helper.DropboxUploaderHelper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import service.StudentService;
import exception.YearbookException;

/**
 * @author nhchdhr
 * 
 * This class represents the Student entity and provides getters and setters for its fields
 * Student entity is mapped to Student table in the database 
 *
 */
@Stateless
public class Student extends bean.Photograph{
	
	private long buckId;
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private String password;
	
	private String dob;
	
	private String gradYear;
	
	private String contactNumber;
	
	private String jobInternDetails;
	
	private String email;
	
	private int deptId;
	
	private String photoUrl;
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	private String selectedDeptId;


	public String getSelectedDeptId() {
		return selectedDeptId;
	}

	public void setSelectedDeptId(String selectedDeptId) {
		this.selectedDeptId = selectedDeptId;
	}

	@EJB
	StudentService studentService;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJobInternDetails() {
		return jobInternDetails;
	}
	public void setJobInternDetails(String jobInternDetails) {
		this.jobInternDetails = jobInternDetails;
	}
	public long getBuckId() {
		return buckId;
	}
	public void setBuckId(long buckId) {
		this.buckId = buckId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGradYear() {
		return gradYear;
	}
	public void setGradYear(String gradYear) {
		this.gradYear = gradYear;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	
	@Override
	public String toString() {
		StringBuffer  sb = new StringBuffer();
		sb.append("The details of the Student are: \nid : " + this.buckId);
		sb.append("\nName : " + firstName + " " + lastName);
		sb.append("\nDate of birth : " + dob);
		sb.append("\nYear of graduation: " + dob + "\n");
		return sb.toString();
	}
	
	
	public String addStudent() throws YearbookException{
		try{
			System.out.println("Inside add");
			System.out.println(this.buckId);
			System.out.println("Inside add 1 - "+this.file);
			System.out.println(this.file.getFileName());
			copyFile(this.file.getFileName(), this.file.getInputstream(), "Student");
	
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			String dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Student");
			System.out.println("Dropbox "+dropboxUrl);
			boolean ret = studentService.addStudent(this.buckId, this.contactNumber, Integer.parseInt(this.selectedDeptId), this.dob,
					this.email, this.firstName, this.gradYear, this.jobInternDetails, this.lastName, 
					this.password, this.username, dropboxUrl);
			dropboxUploader.fetchFromDropBox(dropboxUrl);
			ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//images//downloads"+dropboxUrl);
			System.out.println(filePath);
			this.photoUrl = filePath;
			//dropboxUploader.fetchFromDropBox(dropboxUrl);
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
		
	}
	/*
	
	public String getStudent() throws YearbookException{
		try{
			entity.Student student = studentService.getStudent(this.buckId);
			if(student!=null){
				this.buckId = student.getBuckId();
				this.contactNumber = student.getContactNumber();
				this.deptId = student.getDeptId();
				this.dob = student.getDob();
				this.email = student.getEmail();
				this.firstName = student.getFirstName();
				this.gradYear = student.getGradYear();
				this.jobInternDetails = student.getJobInternDetails();
				this.lastName = student.getLastName();
				this.password = student.getPassword();
				
				DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
				dropboxUploader.fetchFromDropBox(student.getPhotoUrl());

		    	ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
				String filePath = extContext.getRealPath("//images//downloads"+student.getPhotoUrl());
				System.out.println(filePath);
				this.photoUrl = filePath;
				return "true";
			}else 
				return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}*/
	
	
	public String modifyStudent() throws YearbookException{
		try{
			System.out.println("Inside modify student");
			String dropboxUrl = null;
			if(this.file!=null){
				System.out.println(this.file.getFileName());
				copyFile(this.file.getFileName(), this.file.getInputstream(),"Student");
			
				DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
				dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Student");
				System.out.println("Dropbox "+dropboxUrl);
			}
			System.out.println("password "+this.password);
			boolean ret = studentService.updateStudent(this.buckId, this.contactNumber, this.deptId, this.dob,
					this.email, this.firstName, this.gradYear, this.jobInternDetails, this.lastName, 
					this.password, this.username, dropboxUrl);
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	public String deleteStudent() throws YearbookException{
		try{
			System.out.println("Inside delete student");
			boolean ret = studentService.deleteStudent(this.buckId);
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	 public String logStudent() throws YearbookException{
		 try{
			entity.Student loggedInStudent = studentService.login(username,password);
			
	    	if(loggedInStudent != null){
	    		this.buckId = loggedInStudent.getBuckId();
		    	this.contactNumber = loggedInStudent.getContactNumber();
		    	this.deptId = loggedInStudent.getDeptId();
		    	this.dob = loggedInStudent.getDob();
		    	this.email = loggedInStudent.getEmail();
		    	this.firstName = loggedInStudent.getFirstName();
		    	this.gradYear = loggedInStudent.getGradYear();
		    	this.jobInternDetails = loggedInStudent.getJobInternDetails();
		    	this.lastName = loggedInStudent.getLastName();
		    	this.password = loggedInStudent.getPassword();
		    	System.out.println(this.password);
		    	DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
				dropboxUploader.fetchFromDropBox(loggedInStudent.getPhotoUrl());
		    	ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
				String filePath = extContext.getRealPath("//images//downloads"+loggedInStudent.getPhotoUrl());
				System.out.println(filePath);
				this.photoUrl = filePath;
	    		return "true";
	    	}else{
	    		return "false";
	    	}
		 }catch(Exception e){
				System.out.println(e.getMessage());
			}
			return "false";
	    }
	 
	 public boolean equals(Object other)
		{
		    return other instanceof Department && (selectedDeptId != null) ? selectedDeptId.equals(((Department) other).getDeptId()) : (other == this);
		}

		public int hashCode()
		{
		    return selectedDeptId != null ? this.getClass().hashCode() + selectedDeptId.hashCode() : super.hashCode();
		}
}
