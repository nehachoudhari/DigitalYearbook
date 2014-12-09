package bean;

import helper.DropboxUploaderHelper;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import service.DepartmentService;
import exception.YearbookException;

public class DispDepartmentList {
	
	String type;
	
	@EJB
	DepartmentService deptService;
	

	List<SelectItem> allDepartments = null;

	List<entity.Department> deptList = null;
	
	public List<entity.Department> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<entity.Department> deptList) {
		this.deptList = deptList;
	}


	public List<SelectItem> getAllDepartments() {
		return allDepartments;
	}

	public void setAllDepartments(List<SelectItem> allDepartments) {
		this.allDepartments = allDepartments;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	private StreamedContent dbImage;
	
	public StreamedContent getDbImage() {
		return dbImage;
	}
	public void setDbImage(StreamedContent dbImage) {
		this.dbImage = dbImage;
	}
	
	//@PostConstruct
	public String init() {
		allDepartments = new ArrayList<SelectItem>();
		Collection<entity.Department> list;
		
		try {
			list = deptService.getAllDepartments();
			
			if(list!= null) {
				deptList = new ArrayList<entity.Department>(list);
				DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
				dropboxUploader.fetchFromDropBoxIntoYearbook(deptList.get(0).getPhotoUrl());
				ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
				String filePath = extContext.getRealPath("//images//yearbook"+deptList.get(0).getPhotoUrl());
				deptList.get(0).setPhotoUrl(filePath);
				File dispFile = new File(filePath);
				dbImage = null;
				dbImage = new DefaultStreamedContent(new FileInputStream(dispFile), "image/jpg");
				for(entity.Department d : list) {
					allDepartments.add(new SelectItem(d.getDeptId(),d.getName()));
				}
			}
			return "department";
		} catch (YearbookException e) {
			e.printStackTrace();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
    }
	
	
}
