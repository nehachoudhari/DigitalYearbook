package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceContext;

import service.CommitteeMemberService;
import service.StudentService;
import exception.YearbookException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@Named
@SessionScoped
public class StudentList implements Serializable{
	
	@EJB
	StudentService studentService;
	
//	@EJB
//	Student studentBean;
//	
//	public Student getStudentBean() {
//		return studentBean;
//	}
//
//	public void setStudentBean(Student studentBean) {
//		this.studentBean = studentBean;
//	}

	List<entity.Student> allStudents = null;

	public List<entity.Student> getAllStudents() {
		return allStudents;
	}
	
	public void setAllStudents(List<entity.Student> allStudents) {
		this.allStudents = allStudents;
	}

	@PostConstruct
	public void init() {
		//System.out.println("the value od fept id is now" + this.getSessionDeptId());
		//System.out.println("getting all students for dept id " + studentBean.getDeptId());
		try {		
			allStudents = studentService.getAllStudents();
			System.out.println("the number of students found is "+ allStudents.size());
		}catch (YearbookException e) {
			e.printStackTrace();
		}
    }
}
