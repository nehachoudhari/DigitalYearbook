package bean;
import helper.DropboxUploaderHelper;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
	
	private String photoUrl;
		
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
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
	
	private String selectedDeptId;


	public String getSelectedDeptId() {
		return selectedDeptId;
	}

	public void setSelectedDeptId(String selectedDeptId) {
		this.selectedDeptId = selectedDeptId;
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
			System.out.println("Inside modify department");
			String dropboxUrl = null;
			if(this.file!=null){
				System.out.println(this.file.getFileName());
				copyFile(this.file.getFileName(), this.file.getInputstream(),"Department");
				DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
				dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Department");
				System.out.println("Dropbox "+dropboxUrl);
			}
			boolean ret = deptService.updateDepartment(deptId, location, mission, name, url,  dropboxUrl);
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
		try{
			System.out.println("Inside delete department");
			boolean ret = deptService.deleteDepartment(deptId);
			if(ret)
				return "true";
			else 
				return "false";
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return "false";
	}

	public String showDepartment() throws YearbookException{
		try {
			System.out.println("Inside department list");
			System.out.println("Department id  - "+selectedDeptId);
			entity.Department dept = deptService.getDepartment(Integer.parseInt(selectedDeptId));
			deptId = dept.getDeptId();
			location = dept.getLocation();
			mission = dept.getMission();
			name = dept.getName();
			url = dept.getUrl();

			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			dropboxUploader.fetchFromDropBox(dept.getPhotoUrl());
			ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//images//downloads"+dept.getPhotoUrl());
			System.out.println(filePath);
			this.photoUrl = filePath;
			System.out.println();
		} catch (YearbookException e) {
			e.printStackTrace();
		}
		return "true";
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
