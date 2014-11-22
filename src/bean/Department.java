package bean;
import java.util.ArrayList;
import java.util.List;

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

public class Department {
	
	@EJB
	DepartmentService deptService;
	
	private int deptId;
	
	private String name;

	private String mission;
	
	private String location;
	
	private String url;
	
	private List<bean.Photograph> photoList = new ArrayList<bean.Photograph>();
	
	
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
	
	private List<entity.Photograph> convertEntityToBean(List<bean.Photograph> photographs){
		List<entity.Photograph> photoList = new ArrayList<entity.Photograph>();
		for (bean.Photograph photo : photographs){
			entity.Photograph photoIn = new entity.Photograph();
			photoIn.setDetails(photo.getDetails());
			photoIn.setPhotoId(photo.getPhotoId());
			photoIn.setType(photo.getType());
			photoIn.setTypeId(photo.getTypeId());
			photoIn.setUrl(photo.getUrl());
			photoList.add(photoIn);
		}
		return photoList;
	}
	
	public String add() throws YearbookException{
		
		List<entity.Photograph> photos = convertEntityToBean(photoList);
		String ret = deptService.addDepartment(deptId, location, mission, name, url,  photos);

		if(!ret.equalsIgnoreCase("Exists"))
			return "true";
			else 
				return "false";
	}

}
