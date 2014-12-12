package bean;


import javax.ejb.EJB;

import service.DepartmentService;
import exception.YearbookException;

public class CurrentDepartment extends Photograph 
{
	@EJB
	DepartmentService deptService;
	
	@EJB
	Student studentBean;
	
	private int deptId;
	
	private String name;

	private String mission;
	
	private String location;
	
	private String url;
	
	private String photoUrl;
	
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
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	
	public String getDepartmentInfo() {
		entity.Department dept;		
		try {
			System.out.println("Inside current department bean!!");
			System.out.println("Department service "+deptService);
			System.out.println("Student "+studentBean);
			System.out.println("Student " + studentBean.getFirstName());
			System.out.println("Department " + studentBean.getDeptId());
			dept = deptService.getDepartment(studentBean.getDeptId());
			
			System.out.println(dept.getName());
			setName(dept.getName());
			setLocation(dept.getLocation());
			setMission(dept.getMission());
			setUrl(dept.getUrl());
			setPhotoUrl(dept.getPhotoUrl());
			setDeptId(dept.getDeptId());
			return "true";
		} catch (YearbookException e) {
			e.printStackTrace();
		}
		return "false";
    }
	
}
