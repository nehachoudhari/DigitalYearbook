package bean;
import helper.DropboxUploaderHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
	/*
	public void handleFileUpload(FileUploadEvent event) {
       try{
    	   System.out.println("Inside handle listener");
    	   FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
           FacesContext.getCurrentInstance().addMessage(null, msg);  
    	   UploadedFile file = event.getFile();

	       this.fileName = file.getFileName();
	       this.filePath = System.getProperty("user.dir")+"/WebContent/WEB-INF/images/uploads/Department/";
	       copyFile(this.fileName, this.filePath, event.getFile().getInputstream());
	       System.out.println("File path "+this.filePath);
	       System.out.println("File name "+this.fileName);
       }catch(Exception e){
    	   System.out.println(e.getMessage());
       }
    }
	*/
	private void copyFile(String fileName,  InputStream in) {
		try {
			ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//WEB-INF//images//uploads//Department//" + fileName);
			System.out.println("File is "+extContext.getRealPath("//WEB-INF//images//uploads//Department//" + fileName));
			OutputStream out = new FileOutputStream(new File(filePath));
			System.out.println("Ready to write file");
			int read = 0;
			byte[] bytes = new byte[6124];
			while (true) {
				read = in.read(bytes);
				if (read < 0) {
					break;
				}
				out.write(bytes, 0, read);
				out.flush();
			}
			in.close();
			out.flush();
			out.close();
			System.out.println("New file created!");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

	public String addDepartment() throws YearbookException{
		try{ 
			System.out.println("Inside add department");
			System.out.println(this.file);
			List<entity.Photograph> photos = new ArrayList<entity.Photograph>();
			entity.Photograph photo = new Photograph();
			photo.setDetails(details);
			photo.setType("Department");
			System.out.println(this.file.getFileName());
			copyFile(this.file.getFileName(), this.file.getInputstream());
			
	
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			String dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "//"+photo.getType());
			System.out.println("Dropbox "+dropboxUrl);
			photo.setUrl(dropboxUrl);
			photos.add(photo);
			String ret = deptService.addDepartment(deptId, location, mission, name, url,  photos);
			dropboxUploader.fetchFromDropBox(dropboxUrl);
			if(!ret.equalsIgnoreCase("Exists"))
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}

}
