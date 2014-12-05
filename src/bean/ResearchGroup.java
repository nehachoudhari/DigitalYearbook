package bean;

import helper.DropboxUploaderHelper;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import service.ResearchGroupService;
import exception.YearbookException;


/**
 * @author nhchdhr
 * 
 * This class represents the Research Group entity and provides getters and setters for its fields
 * Student entity is mapped to ResearchGroup table in the database 
 *
 */
public class ResearchGroup extends Photograph{
	
	@EJB
	ResearchGroupService researchService;
	
	@EJB
	Student studentBean;

	private int groupId;
	private String name;
	private String description;
	private int deptId;
	private String photoUrl;
	private String url;
	private String selectedResId;
	
	public String getSelectedResId() {
		return selectedResId;
	}
	public void setSelectedResId(String selectedResId) {
		this.selectedResId = selectedResId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
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
		try{
			System.out.println("Inside add");
			System.out.println("Inside add 1 - "+this.file);
			System.out.println(this.file.getFileName());
			copyFile(this.file.getFileName(), this.file.getInputstream(), "Research");
	
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			String dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Research");
			System.out.println("Dropbox "+dropboxUrl);
			System.out.println(studentBean);
			boolean ret = researchService.addResearchGroup(name, studentBean.getDeptId(), description, url, dropboxUrl);
			
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
		try{
			System.out.println("Inside modify");
			System.out.println(this.file.getFileName());
			String dropboxUrl = null;
			if(this.file!=null){
				System.out.println(this.file.getFileName());
				copyFile(this.file.getFileName(), this.file.getInputstream(),"Research");
			
				DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
				dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Research");
				System.out.println("Dropbox "+dropboxUrl);
			}
			System.out.println(studentBean);
			boolean ret = researchService.updateResearchGroup(groupId,name, deptId, description, url, dropboxUrl);
			
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	public String delete() throws YearbookException{
		try{
			System.out.println("Inside delete group ");
			
			boolean ret = researchService.deleteReseachGroup(groupId);
			
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	public String showGroup() throws YearbookException{
		try {
			System.out.println("Inside member list");
			System.out.println("Research id  - "+selectedResId);
			entity.ResearchGroup research = researchService.getResearchGroup(Integer.parseInt(selectedResId));
			this.deptId = research.getDeptId();
			this.description = research.getDescription();
			this.groupId = research.getGroupId();
			this.name = research.getName();
			this.url = research.getUrl();

			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			dropboxUploader.fetchFromDropBox(research.getPhotoUrl());

	    	ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//images//downloads"+research.getPhotoUrl());
			System.out.println(filePath);
			this.photoUrl = filePath;
		} catch (YearbookException e) {
			e.printStackTrace();
		}
		return "true";
    }

	public boolean equals(Object other)
	{
	    return other instanceof ResearchGroup && (selectedResId != null) ? selectedResId.equals(((ResearchGroup) other).getGroupId()) : (other == this);
	}

	public int hashCode()
	{
	    return selectedResId != null ? this.getClass().hashCode() + selectedResId.hashCode() : super.hashCode();
	}
}



