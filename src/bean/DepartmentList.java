package bean;

import helper.DropboxUploaderHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import service.DepartmentService;
import exception.YearbookException;

public class DepartmentList {
	
	String type;
	
	@EJB
	DepartmentService deptService;
	
	@EJB
	Student studentBean;

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

	@PostConstruct
	public void init() {
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
				for(entity.Department d : list) {
					allDepartments.add(new SelectItem(d.getDeptId(),d.getName()));
				}
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
	
	
}
