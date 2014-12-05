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
			System.out.println(deptService);
			list = deptService.getAllDepartments();
			
			if(list!= null) {
				deptList = new ArrayList<entity.Department>(list);
				for(entity.Department d : list) {
					allDepartments.add(new SelectItem(d.getDeptId(),d.getName()));
				}
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
	
	
}
