package bean;
import javax.ejb.EJB;

import org.primefaces.model.UploadedFile;

import service.StudentService;
import exception.YearbookException;

/**
 * @author nhchdhr
 * 
 * This class represents the Student entity and provides getters and setters for its fields
 * Student entity is mapped to Student table in the database 
 *
 */
public class Student extends Photograph{
	
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
	
	private UploadedFile  file;
	
	private String details;
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public UploadedFile  getFile() {
		return file;
	}
	public void setFile(UploadedFile  file) {
		this.file = file;
	}
	
	private int deptId;
	
	private Photograph photo = new Photograph();
	
	@EJB
	StudentService studentService ;
	
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
		entity.Photograph photoEntity = convertEntityToBean(photo);
		String ret = studentService.addStudent(buckId, contactNumber, deptId, dob, email,
				firstName, gradYear, jobInternDetails, lastName,
				  password, username, photoEntity);

		if(!ret.equalsIgnoreCase("Exists"))
			return "true";
		else 
			return "false";
	}
	public String modifyStudent() throws YearbookException{
		String ret = "true";

		if(!ret.equalsIgnoreCase("Exists"))
			return "true";
		else 
			return "false";
	}
	
	public String deleteStudent() throws YearbookException{
		String ret = "Success";

		if(!ret.equalsIgnoreCase("Success"))
			return "delete";
		else 
			return "false";
	}
}
