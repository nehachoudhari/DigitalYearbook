package bean;

import exception.YearbookException;


/**
 * @author nhchdhr
 * 
 * This class represents the Research Group entity and provides getters and setters for its fields
 * Student entity is mapped to ResearchGroup table in the database 
 *
 */
public class ResearchGroup extends Photograph{

	private int groupId;
	private String name;
	private String description;
	private int deptId;
	private String photoUrl;
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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



