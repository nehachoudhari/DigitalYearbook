package bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import service.DepartmentService;
import exception.YearbookException;

public class DepartmentList {
	
	String type;
	
	@EJB
	DepartmentService deptService;

	List<SelectItem> allDepartments = null;
	
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
			System.out.println(deptService);
			list = deptService.getAllDepartments();
			if(list!= null) {
				for(entity.Department d : list) {
					allDepartments.add(new SelectItem(d.getDeptId(),d.getName()));
				}
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
	
	
}
