package bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import service.DepartmentService;
import exception.YearbookException;

public class Lists extends bean.Photograph {
	
	String type;
	
	@EJB
	DepartmentService deptService;

	List<String> allDepartments = null;
	


	public List<String> getAllDepartments() {
		return allDepartments;
	}

	public void setAllDepartments(List<String> allDepartments) {
		this.allDepartments = allDepartments;
	}
	/*
	public Lists() {
		loadDropdown();
	}*/
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	public void loadDropdown() {
		allDepartments = new ArrayList<SelectItem>();
		
		allDepartments.add(new SelectItem("1","ab"));
		allDepartments.add(new SelectItem("2","bc"));
		allDepartments.add(new SelectItem("3","cd")); 
		
		Collection<entity.Department> list;
		try {
			System.out.println(deptService);
			list = deptService.getAllDepartments();
			if(list!= null) {
				for(entity.Department d : list) {
					allDepartments.add(d.getName());
				}
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
	}*/
	
	@PostConstruct
	public void init() {
		allDepartments = new ArrayList<String>();
		Collection<entity.Department> list;
		try {
			System.out.println(deptService);
			list = deptService.getAllDepartments();
			if(list!= null) {
				for(entity.Department d : list) {
					allDepartments.add(d.getName());
				}
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
}
