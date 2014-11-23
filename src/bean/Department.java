package bean;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import service.DepartmentService;
import entity.Photograph;
import exception.YearbookException;


/**
 * @author nhchdhr
 * 
 * This class represents the Department entity and provides getters and setters for its fields
 * Student entity is mapped to Department table in the database 
 *
 */

public class Department {
	
	@EJB
	DepartmentService deptService;
	
	private int deptId;
	
	private String name;

	private String mission;
	
	private String location;
	
	private String url;
	
	private UploadedFile  file;
	
	public UploadedFile  getFile() {
		return file;
	}
	public void setFile(UploadedFile  file) {
		this.file = file;
	}

	private String details;
	
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	
	
	public String toString() {
		StringBuffer  sb = new StringBuffer();
		sb.append("The Department details are : \nDept id: " + this.deptId);
		sb.append("\nName : " + name);
		sb.append("\nMission : " + mission + "\n");
		return sb.toString();
	}
	
	private entity.Photograph convertEntityToBean(bean.Photograph photographs){
		/*List<entity.Photograph> photoList = new ArrayList<entity.Photograph>();
		for (bean.Photograph photo : photographs){*/
			entity.Photograph photoIn = new entity.Photograph();
			photoIn.setDetails(photographs.getDetails());
			photoIn.setType(photographs.getType());
			photoIn.setTypeId(photographs.getTypeId());
			photoIn.setUrl(photographs.getFile().getPath());
			return photoIn;
			/*photoList.add(photoIn);
		}
		return photoList;*/
	}
	
	public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
       this.file = file;
    }
	
	public String addDepartment() throws YearbookException{
		
		List<entity.Photograph> photos = new ArrayList<entity.Photograph>();
		entity.Photograph photo = new Photograph();
		photo.setDetails(details);
		photo.setType("department");
		photo.setUrl(file.getFileName());
		System.out.println(file.getFileName());

		System.out.println(file.getContentType());
		String ret = deptService.addDepartment(deptId, location, mission, name, url,  photos);

		if(!ret.equalsIgnoreCase("Exists"))
			return "true";
			else 
				return "false";
	}

}
