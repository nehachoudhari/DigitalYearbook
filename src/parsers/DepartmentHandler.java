package parsers;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bean.Department;

 
public class DepartmentHandler extends DefaultHandler {
    
    private List<Department> departments = null;
    private Department department;

    public List<Department> getDepartmentList() {
        return departments;
    }
 
    boolean mission = false;
    boolean name = false;
    boolean deptid = false;
    boolean location = false;
    boolean url = false;
 
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
 
    	if(qName.equalsIgnoreCase("Department")) {
        	
        	department = new Department();
        	if(departments == null) {
        		departments = new ArrayList<Department>();
        	}
        } else if(qName.equalsIgnoreCase("mission")) {
        	mission = true;
        } else if(qName.equalsIgnoreCase("name")) {
        	name = true;
        } else if(qName.equalsIgnoreCase("id")) {
        	deptid = true;
        } else if(qName.equalsIgnoreCase("location")) {
        	location = true;
        } else if(qName.equalsIgnoreCase("url")) {
        	url = true;
        }
    }
 
 
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
       
    	if(qName.equalsIgnoreCase("Department")) {
        	departments.add(department);
        }
    }
 
 
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
 
        if (mission) {
        	department.setMission(new String(ch, start, length));
        	mission = false;
        } else if (name) {
        	department.setName(new String(ch, start, length));
        	name = false;
        } else if (deptid) {
        	department.setDeptId(Integer.parseInt(new String(ch, start, length)));
        	deptid = false;
        } else if (location) {
        	department.setLocation(new String(ch, start, length));
        	location = false;
        } else if (url) {
        	department.setUrl(new String(ch, start, length));
        	url = false;
        }
     } 
}