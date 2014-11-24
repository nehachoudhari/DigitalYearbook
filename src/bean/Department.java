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
	
	//private UploadedFile  file;
	
	private String filePath;
	
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}/*
	public UploadedFile  getFile() {
		return file;
	}
	public void setFile(UploadedFile  file) {
		this.file = file;
	}*/

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
       try{
    	   UploadedFile file = event.getFile();

	       this.fileName = file.getFileName();
	       this.filePath = System.getProperty("user.dir")+"/WebContent/WEB-INF/images/uploads/";
	       copyFile(fileName, filePath, event.getFile().getInputstream());
       }catch(Exception e){
    	   System.out.println(e.getMessage());
       }
    }
	
	private void copyFile(String fileName, String filePath, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(filePath+fileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
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
		
		List<entity.Photograph> photos = new ArrayList<entity.Photograph>();
		entity.Photograph photo = new Photograph();
		photo.setDetails(details);
		photo.setType("department");
		photo.setUrl(System.getProperty("user.dir")+"/WebContent/WEB-INF/images/uploads/" + this.filePath);
		System.out.println(this.fileName);

		System.out.println(this.fileName);

		DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
		String url = dropboxUploader.uploadToDropBox(this.filePath,this.fileName, photo.getType());
		String ret = deptService.addDepartment(deptId, location, mission, name, url,  photos);
		
		if(!ret.equalsIgnoreCase("Exists"))
			return "true";
			else 
				return "false";
	}

}
