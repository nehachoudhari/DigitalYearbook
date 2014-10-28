package parsers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bean.Photograph;


public class PhotoHandler extends DefaultHandler 
{
	String content=null;
	Photograph pic = null;	
	List<Photograph> piclist= new ArrayList<>();
	
	public List<Photograph> getPhotosList() {
		return piclist;
	}
	
	
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException
	{	
		if(qName.equalsIgnoreCase("photo"))
			pic = new Photograph();
		
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
	     content = String.copyValueOf(ch, start, length).trim();
	}
	
	public void endElement(String uri, String localName,String qName) throws SAXException 
	{
		switch(qName)
		{
		case "photo":
			piclist.add(pic);
			break;
		case "photoId":
			pic.setPhotoId(Long.parseLong(content));
			break;
		case "photo_url":
			pic.setUrl(content);
			break;
		case "photo_details":
			pic.setDetails(content);
			break;
		case "type":
			pic.setType(content);
			break;
		case "typeId":
			pic.setTypeId(Long.parseLong(content));
			break;
		}
	}
}