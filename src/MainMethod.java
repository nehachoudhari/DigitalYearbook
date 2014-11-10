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

	public static void main(String args[]) throws IOException,
			YearbookException, Exception {
		try {
			// initialize the empty hash tables
			initialiseHashTables();

			// validate and load photographs from XML
			boolean validate = false;
			validate = XMLValidator.validateXMLFromXSD("Photographs.xml",
					"Photographs.xsd");
			if (validate) {
				photographs = PhotoParser.parsePhotos("Photographs.xml");
				arrangePhotos();
			}

			// for user input
			BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

			// loop till user chooses to exit
			while (true) {
				System.out.println("1 : Student \n2 : Department \n0 : Exit");

				// choice of data table
				entity = Integer.parseInt(b.readLine());

				if (entity == 0) {
					System.out.print("Goodbye!");
					System.exit(0);
				}

				System.out.println("1 : ADD \n2 : MODIFY \n3 : DELETE \n0 : Exit");
				// choice of operation on the chosen table
				action = Integer.parseInt(b.readLine());

				if (action == 0) {
					System.out.print("Goodbye!");
					System.exit(0);
				}

				switch (entity) {
				case 1:
					Entity = "Student";

					switch (action) {
					case 1:
						Action = "Add";

						// check if the XML is valid for XML schema document
						validate = XMLValidator.validateXMLFromXSD("Student.xml", "Student.xsd");

						// if valid, perform the requested operation
						if (validate) {

							// parse the XML and read student objects
							students = StudentParser.parseStudents("Student.xml");
							for (Student s : students) {

								// get photo for this student
								List<Photograph> l = (List<Photograph>) getPhotos(s);
								if (l == null || l.size() == 0) {
									// cannot insert if no photograph is found for this student
									System.out.println("No photogrpah exists for this student id: "+ s.getBuckId());
									continue;
								}

								// add this student to the database
								studentDao.addStudent(s, l.get(0));
								System.out.println("Student added : " + s.getFirstName());
							}
						} else {
							System.out.println("Error validating XML");
						}

						break;
					case 2:
						Action = "Modify";

						// check if the XML is valid for XML schema document
						validate = XMLValidator.validateXMLFromXSD(
								"ModifyStudent.xml", "Student.xsd");

						// if valid, perform the requested operation
						if (validate) {
							students = StudentParser.parseStudents("ModifyStudent.xml");
							for (Student s : students) {
								
								//TODO for modify the list of photographs should be read from the table and not from "photos"
								List<Photograph> l = (List<Photograph>) getPhotos(s);
								if (l == null) {
									System.out.println("No photogrpah exists for this student id: " + s.getBuckId());
									continue;
								}
								studentDao.updateStudent(s, l.get(0));
								System.out.println("Student details updated");
							}
						} else {
							System.out.println("Error validating XML");
						}

						break;
					case 3:
						Action = "Delete";
						validate = XMLValidator.validateXMLFromXSD("DeleteStudent.xml", "Student.xsd");
						if (validate) {
							students = StudentParser.parseStudents("DeleteStudent.xml");
							for (Student s : students) {
								studentDao.deleteStudent(s);
								System.out.println("Student details deleted");
								}
						} else {
							System.out.println("Error validating XML");
						}

						break;
					}

					break;
				case 2:
					Entity = "Department";

					switch (action) {
					case 1:
						Action = "Add";
						validate = XMLValidator.validateXMLFromXSD("Department.xml", "Department.xsd");
						if (validate) {
							departments = DepartmentParser.parseDepartments("Department.xml");
							for (Department d : departments) {

								depDao.addDepartment(d, getPhotos(d));
								System.out.println("Department details added :"+ d.getName());
							}
						} else {
							System.out.println("Error validating XML");
						}

						break;
					case 2:
						Action = "Modify";

						validate = XMLValidator.validateXMLFromXSD("ModifyDepartment.xml", "Department.xsd");
						if (validate) {
							departments = DepartmentParser.parseDepartments("ModifyDepartment.xml");
							for (Department d : departments) {

								depDao.updateDepartment(d, getPhotos(d));
							}
						} else {
							System.out.println("Error validating XML");
						}

						break;
					case 3:
						Action = "Delete";

						validate = XMLValidator.validateXMLFromXSD("DeleteDepartment.xml", "Department.xsd");
						if (validate) {
							departments = DepartmentParser.parseDepartments("Department.xml");
							for (Department d : departments) {

								depDao.deleteDepartment(d);
							}
						} else {
							System.out.println("Error validating XML");
						}
						break;
					}
					break;
					}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method initializes the empty hash table of photos This hash table is
	 * a list of key value pairs where key is a String indicating the type of
	 * the entity and the value is a hash table of key values id of the Entity
	 * element, list of photographs This convention is used because some of the
	 * entities have more than one photographs Example: key, value : student,
	 * Hash_table<buckId, list > key, value : department, Hash_table <deptId,
	 * list >
	 */
	public static void initialiseHashTables() {
		photos = new Hashtable<>();
		photos.put("student", new Hashtable<Long, List<Photograph>>());
		photos.put("member", new Hashtable<Long, List<Photograph>>());
		photos.put("department", new Hashtable<Long, List<Photograph>>());
		photos.put("event", new Hashtable<Long, List<Photograph>>());
		photos.put("research", new Hashtable<Long, List<Photograph>>());
	}

	/**
	 * This method iterates over the photographs objects obtained by parsing the
	 * XML and adds each photograph to corresponding item in the hash table:
	 * photos
	 */
	public static void arrangePhotos() {
		for (Photograph p : photographs) {

			String type = p.getType().toLowerCase();
			if (photos.containsKey(type)) {
				Hashtable<Long, List<Photograph>> hash = photos.get(type);
				if (hash.get(p.getTypeId()) == null) {
					hash.put(p.getTypeId(), new ArrayList<Photograph>());
				}
				List<Photograph> list = hash.get(p.getTypeId());
				list.add(p);
				hash.put(p.getTypeId(), list);
				photos.put(type, hash);
			}
		}
	}

	/**
	 * This method gives the photographs belonging to current object It checks
	 * the type of current object and fetches the corresponding hash table entry
	 * from "photos"
	 * 
	 * @param type:
	 *            Object, could be of any entity type, 
	 *            for example : student/department
	 * @return list of photographs for this object
	 */
	public static List<Photograph> getPhotos(Object type) {
		List<Photograph> list = null;
		if (type instanceof Student) {
			Hashtable<Long, List<Photograph>> hash = photos.get("student");
			list = hash.get(new Long(((Student) type).getBuckId()));
		} else if (type instanceof Department) {
			Hashtable<Long, List<Photograph>> hash = photos.get("department");
			list = hash.get(new Long(((Department) type).getDeptId()));
		}
		return list;
	}
	
	

}
