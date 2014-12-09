package bean;

import helper.DropboxUploaderHelper;

import java.io.File;
import java.io.FileInputStream;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class DispStudentList implements Serializable{
	
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
	List<Student> allStudents;
	public List<Student> getAllStudents() {
		return allStudents;
	}
	public void setAllStudents(List<Student> allStudents) {
		this.allStudents = allStudents;
	}
	public List<entity.Student> getAllStudentsEntity() {
		return allStudentsEntity;
	}
	public void setAllStudentsEntity(List<entity.Student> allStudentsEntity) {
		this.allStudentsEntity = allStudentsEntity;
	}
	List<entity.Student> allStudentsEntity = null;
	


	private StreamedContent dbImage;
	
	public StreamedContent getDbImage() {
		return dbImage;
	}
	public void setDbImage(StreamedContent dbImage) {
		this.dbImage = dbImage;
	}
	
	private Student convertToBean(entity.Student entityBean){
		Student student = new Student();
		try {			
			student.setBuckId(entityBean.getBuckId());
			student.setContactNumber(entityBean.getContactNumber());
			student.setDeptId(entityBean.getDeptId());
			student.setDob(entityBean.getDob());
			student.setEmail(entityBean.getEmail());
			student.setFirstName(entityBean.getFirstName());
			student.setGradYear(entityBean.getGradYear());
			student.setJobInternDetails(entityBean.getJobInternDetails());
			student.setLastName(entityBean.getLastName());
			student.setPassword(entityBean.getPassword());
			student.setUsername(entityBean.getUsername());
			
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			dropboxUploader.fetchFromDropBoxIntoYearbook(entityBean.getPhotoUrl());
			ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//images//yearbook"+entityBean.getPhotoUrl());
			File dispFile = new File(filePath);
			StreamedContent dbImage = null;
			dbImage = new DefaultStreamedContent(new FileInputStream(dispFile), "image/jpg");
			student.setDbImage(dbImage);
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return student;
	}
	//@PostConstruct
	public String init() {
		try {		
			allStudentsEntity = studentService.getAllStudents();
			allStudents = new ArrayList<Student>();
			for(entity.Student s: allStudentsEntity) {
				allStudents.add(convertToBean(s));				
			}
			
			System.out.println("the number of students found is "+ allStudentsEntity.size());
			return "student";
		}catch (YearbookException e) {
			e.printStackTrace();
		}
		return "false";
    }
}
