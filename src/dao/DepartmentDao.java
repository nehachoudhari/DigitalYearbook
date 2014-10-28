package dao;

import java.util.List;

import bean.Department;
import bean.Photograph;
import exception.YearbookException;

public interface DepartmentDao {
	public int addDepartment(Department department, List<Photograph> photoList) throws YearbookException;
	
	public void updateDepartment(Department department, List<Photograph> photoList) throws YearbookException;
	
	public void deleteDepartment(Department department) throws YearbookException;
}
