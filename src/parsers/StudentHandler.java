package parsers;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bean.Student;

 
public class StudentHandler extends DefaultHandler {
	
//	private List<Photograph> pics ;
//	int i = 0;
 
    private List<Student> students = null;
    private Student student = null;

    public List<Student> getStudentList() {
        return students;
    }
    
    boolean fName = false;
    boolean lName = false;
    boolean gradYear = false;
    boolean dob = false;
    boolean job = false;
    boolean phone = false;
    boolean buckid = false;
    boolean userName = false;
    boolean password = false;
    boolean deptId = false;
    boolean email = false;
    
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
    	
    	if (qName.equalsIgnoreCase("Student")) {
        	 student = new Student();
          
            if (students == null)
            	students = new ArrayList<Student>();
        } else if (qName.equalsIgnoreCase("buckid")) {
        	buckid = true;
        } else if (qName.equalsIgnoreCase("fname")) {
            fName = true;
        } else if (qName.equalsIgnoreCase("lname")) {
            lName = true;
        } else if (qName.equalsIgnoreCase("dob")) {
            dob = true;
        } else if (qName.equalsIgnoreCase("job")) {
            job = true;
        } else if (qName.equalsIgnoreCase("contactnumber")) {
            phone = true;
        } else if (qName.equalsIgnoreCase("gradYear")) {
        	gradYear = true;
        } else if(qName.equalsIgnoreCase("username")) {
        	userName = true;
        } else if(qName.equalsIgnoreCase("password")) {
        	password = true;
        } else if(qName.equalsIgnoreCase("deptId")) {
        	deptId = true;
        } else if(qName.equalsIgnoreCase("email")) {
        	email = true;
        }
    }
 
 
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Student")) {
        	students.add(student);
        } 
    }
 
 
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
 
        if (fName) {
            student.setFirstName(new String(ch, start, length));
            fName = false;
        } else if (lName) {
            student.setLastName(new String(ch, start, length));
            lName = false;
        } else if (buckid) {
        	student.setBuckId(Long.parseLong(new String(ch, start, length)));
        	buckid = false;
        } else if (gradYear) {
            student.setGradYear(new String(ch, start, length));
            gradYear = false;
        } else if (job) {
            student.setJobInternDetails(new String(ch, start, length));
            job = false;
        } else if (phone) {
        	student.setContactNumber(new String(ch, start, length));
        	phone = false;
        }  else if (dob) {
        	student.setDob(new String(ch, start, length));
        	dob = false;
        } else if (userName) {
        	student.setUsername(new String(ch, start, length));
        	userName = false;
        } else if (password) {
        	student.setPassword(new String(ch, start, length));
        	password = false;
        } else if (deptId) {
        	student.setDeptId(Integer.parseInt(new String(ch, start, length)));
        	deptId = false;
        } else if (email) {
        	student.setEmail(new String(ch, start, length));
        	email = false;
        }
//        } else if (photo) {
//        	if(pics == null) {
//        		pics = SAXPhotoparser.parsePhotos("Student.xml");
//        		i = 0;
//        	}
//        	student.setPic(pics.get(i));
//        	photo = false;
//        	i++;
//        }
     } 
}