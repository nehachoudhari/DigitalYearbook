package bean;
import helper.DropboxUploaderHelper;

import javax.ejb.EJB;

import service.DepartmentService;
import exception.YearbookException;

/**
 * @author nhchdhr
 * 
 * This class represents the Department entity and provides getters and setters for its fields
 * Student entity is mapped to Department table in the database 
 *
 */

public class Department extends bean.Photograph{
	
	@EJB
	DepartmentService deptService;
	
	private int deptId;
	
	private String name;

	private String mission;
	
	private String location;
	
	private String url;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	
	
	public String toString() {
		StringBuffer  sb = new StringBuffer();
		sb.append("The Department details are : \nDept id: " + this.deptId);
		sb.append("\nName : " + name);
		sb.append("\nMission : " + mission + "\n");
		return sb.toString();
	}
	

	public String addDepartment() throws YearbookException{
		try{ 
			System.out.println("Inside add department");
			System.out.println(this.file);
			System.out.println(this.file.getFileName());
			copyFile(this.file.getFileName(), this.file.getInputstream(),"Department");
			
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			String dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Department");
			System.out.println("Dropbox "+dropboxUrl);
			boolean ret = deptService.addDepartment(deptId, location, mission, name, url,  dropboxUrl);
			dropboxUploader.fetchFromDropBox(dropboxUrl);
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	public String modifyDepartment() throws YearbookException{
		
		try{ 
//			System.out.println("Inside modify department");
//			System.out.println(this.file);
//			System.out.println(this.file.getFileName());
			
			String dropboxUrl = "";
			if(this.file.getFileName()!=null || this.file.getFileName() != "") {
				copyFile(this.file.getFileName(), this.file.getInputstream(),"Department");
				
				DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
				dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Department");
			//	System.out.println("Dropbox "+dropboxUrl);
			//	dropboxUploader.fetchFromDropBox(dropboxUrl);
			}
			
			boolean ret = deptService.addDepartment(deptId, location, mission, name, url,  dropboxUrl);
			
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	public String deleteDepartment() throws YearbookException{
		String ret = "true";

		if(!ret.equalsIgnoreCase("Exists"))
			return "true";
		else 
			return "false";
	}

	
}
