package parsers;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.*;

import org.xml.sax.SAXException;

import bean.Student;


public class StudentParser {

	public static List<Student> parseStudents(String fileName) 
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			StudentHandler handler = new StudentHandler();
			saxParser.parse(fileName, handler);
			
			return handler.getStudentList();
		} catch (ParserConfigurationException | SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}
