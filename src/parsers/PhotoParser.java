package parsers;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.*;

import org.xml.sax.SAXException;

import bean.Photograph;


public class PhotoParser {

	public static List<Photograph> parsePhotos(String fileName) 
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			PhotoHandler handler = new PhotoHandler();
			saxParser.parse(fileName, handler);
			
			return handler.getPhotosList();
		} catch (ParserConfigurationException | SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
