package parsers;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import bean.Department;


public class DepartmentParser {

	
	public static List<Department> parseDepartments(String fileName) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        DepartmentHandler handler = new DepartmentHandler();
	        
	        saxParser.parse(fileName, handler);
	     
	        return handler.getDepartmentList();  
	        
	     
	        
	    } catch (ParserConfigurationException  e) {
	        e.printStackTrace();
	    } catch (SAXException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }	
	    return null;
	}
	 
}
