package bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import service.CommitteeMemberService;
import service.StudentService;
import exception.YearbookException;

public class StudentList {
	
	@EJB
	StudentService studentService;
	
	@EJB
	Student studentBean;
	
	List<entity.Student> allStudents = null;

	public List<entity.Student> getAllStudents() {
		return allStudents;
	}
	
	public void setAllStudents(List<entity.Student> allStudents) {
		this.allStudents = allStudents;
	}

	@PostConstruct
	public void init() {
		
		try {		
			allStudents = studentService.getAllStudents(studentBean.getDeptId());
		}catch (YearbookException e) {
			e.printStackTrace();
		}
    }
}
