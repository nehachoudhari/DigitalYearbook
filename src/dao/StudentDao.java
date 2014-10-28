package dao;

import exception.YearbookException;
import bean.Photograph;
import bean.Student;

public interface StudentDao{
	public void addStudent(Student student, Photograph photo) throws YearbookException;
	
	public void updateStudent(Student student, Photograph photo) throws YearbookException;
	
	public void deleteStudent(Student student) throws YearbookException;
}
