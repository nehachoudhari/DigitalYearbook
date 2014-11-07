import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import parsers.DepartmentParser;
import parsers.PhotoParser;
import parsers.StudentParser;
import validators.XMLValidator;
import bean.Department;
import bean.Photograph;
import bean.Student;
import constants.PhotoTypeEnum;
import dao.*;
import daoImpl.*;
import exception.YearbookException;



public class MainMethod {
	static int entity = 0;
	static int action = 0;
	static String Entity = null;
	static String Action = null;
	static List<Student> students = new ArrayList<Student>();
	static List<Department> departments = new ArrayList<Department>();
	static List<Photograph> photographs = new ArrayList<Photograph>();
	static Hashtable<String, Hashtable<Long, List<Photograph>>> photos;
	static StudentDao studentDao = new StudentDaoImpl();
	static DepartmentDao depDao = new DepartmentDaoImpl();
	
	
	public static void main(String args[])throws IOException, YearbookException, Exception
	{
		try{
			//initialize the empty hash tables
			initialiseHashTables();
			
			//validate and load photographs from xml
			boolean validate = false;
			validate = XMLValidator.validateXMLFromXSD("Photographs.xml", "Photographs.xsd");
			if (validate) {
				photographs = PhotoParser.parsePhotos("Photographs.xml");
				arrangePhotos();
			}
			
			//for user input
			BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
			
			//loop till user chooses to exit
			while (true) {
				System.out.println("1 : Student \n2 : Department \n3 : Photograph \n0 : Exit" );
				
				//choice of data table 
				entity = Integer.parseInt(b.readLine());
				
				if(entity == 0) {
					System.out.print("Goodbye!");
					System.exit(0);
				}
				
				System.out.println("1 : ADD \n2 : MODIFY \n3 : DELETE \n0 : Exit");
				//choice of operation on the chosen table
				action = Integer.parseInt(b.readLine());
	
				if(action == 0) {
					System.out.print("Goodbye!");
					System.exit(0);
				}
				
				switch (entity)
				{
				case 1: Entity = "Student";
					
					switch (action)
					{
					case 1: Action = "Add";
							validate = XMLValidator.validateXMLFromXSD("Student.xml", "Student.xsd");
							if (validate) {
								students = StudentParser.parseStudents("Student.xml");
								for(Student s : students) {
									List<Photograph> l = (List<Photograph>) getPhotos(s);
									if(l==null) {
										System.out.println("Please add this student's photograph first : " + s.getFirstName());
										continue;
									}	
									studentDao.addStudent(s, getPhotos(s).get(0));
									System.out.println("Student added : " + s.getFirstName());
								}
							} else {
								System.out.println("Error validating xml");
							}
							
							break;
					case 2: Action = "Modify"; 
							validate = XMLValidator.validateXMLFromXSD("ModifyStudent.xml", "Student.xsd");
							if (validate) {
								students = StudentParser.parseStudents("Student.xml");
								for(Student s : students) {
									List<Photograph> l = (List<Photograph>) getPhotos(s);
									if(l==null) {
										System.out.println("Please add this student's photograph first : " + s.getFirstName());
										continue;
									}
									studentDao.updateStudent(s, getPhotos(s).get(0));
									}
							} else {
								System.out.println("Error validating xml");
							}
					
							break;
					case 3: Action = "Delete"; 
							validate = XMLValidator.validateXMLFromXSD("DeleteStudent.xml", "Student.xsd");
							if (validate) {
								students = StudentParser.parseStudents("Student.xml");
								for(Student s : students) {
									List<Photograph> l = (List<Photograph>) getPhotos(s);
									if(l==null)
										continue;
									studentDao.deleteStudent(s);
									
								}
							} else {
								System.out.println("Error validating xml");
							}
								
							
							break;
					}
					
					break;
				case 2: 
					Entity = "Department";
					
					switch (action)
					{
					case 1: Action = "Add";
							validate = XMLValidator.validateXMLFromXSD("Department.xml","Department.xsd");
							if (validate) {
								departments = DepartmentParser.parseDepartments("Department.xml");
								for(Department d : departments) {
									
									depDao.addDepartment(d, getPhotos(d));
								}
							} else {
								System.out.println("Error validating xml");
							}
								
							break;
					case 2: Action = "Modify"; 
					
							validate = XMLValidator.validateXMLFromXSD("ModifyDepartment.xml","Department.xsd");
							if (validate) {
								departments = DepartmentParser.parseDepartments("Department.xml");
								for(Department d : departments) {
									
									depDao.updateDepartment(d, getPhotos(d));
								}
							} else {
								System.out.println("Error validating xml");
							}
								
							
							break;
					case 3: Action = "Delete"; 
					
							validate = XMLValidator.validateXMLFromXSD("DeleteDepartment.xml","Department.xsd");
							if (validate) {
								departments = DepartmentParser.parseDepartments("Department.xml");
								for(Department d : departments) {
									
									depDao.deleteDepartment(d);
								}
							} else {
								System.out.println("Error validating xml");
							}
								
					
						break;
					}
					
					
					break;
				
				}
				
				
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void initialiseHashTables() {
		photos = new Hashtable<>();
		photos.put("student", new Hashtable<Long, List<Photograph>>());
		photos.put(PhotoTypeEnum.MEMBER.toString(), new Hashtable<Long, List<Photograph>>());
		photos.put("department", new Hashtable<Long, List<Photograph>>());
		photos.put(PhotoTypeEnum.EVENT.toString(), new Hashtable<Long, List<Photograph>>());
		photos.put(PhotoTypeEnum.RESEARCH_GROUP.toString(), new Hashtable<Long, List<Photograph>>());
	}

	public static void arrangePhotos() {
		for (Photograph p : photographs) {
			
			String type = p.getType().toLowerCase();
			if(photos.containsKey(type)) {
				Hashtable<Long, List<Photograph>> hash = photos.get(type);
				if(hash.get(p.getTypeId()) == null) {
					hash.put(p.getTypeId(), new ArrayList<Photograph>());
				}
				List<Photograph> list = hash.get(p.getTypeId());
				list.add(p);
				hash.put(p.getTypeId(), list);
				photos.put(type, hash);
			}	
		}
	}
	
	public static List<Photograph> getPhotos(Object type) {
		List<Photograph> list = null;
		if(type instanceof Student) {
			Hashtable<Long, List<Photograph>> hash = photos.get("student");
			list = hash.get(new Long(((Student) type).getBuckId()));
		} else if (type instanceof Department) {
			Hashtable<Long, List<Photograph>> hash = photos.get("department");
			list = hash.get(new Long(((Department) type).getDeptId()));
		}  
		return list;
	}
}
