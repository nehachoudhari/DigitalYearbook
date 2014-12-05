package bean;

import exception.YearbookException;

/**
 * @author nhchdhr
 * 
 * This class represents the Event entity and provides getters and setters for its fields
 * This entity is mapped to Event table in the database 
 *
 */
public class Event extends Photograph{
	
	private String name;
	private String description;
	private String date;
	private String url;
	private long eventId;
	private String photoUrl;
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	public String add() throws YearbookException{
		String ret = "true";

		if(!ret.equalsIgnoreCase("true"))
			return "true";
		else 
			return "false";
	}
	public String modify() throws YearbookException{
		String ret = "true";

		if(!ret.equalsIgnoreCase("true"))
			return "true";
		else 
			return "false";
	}
	
	public String delete() throws YearbookException{
		String ret = "Success";

		if(!ret.equalsIgnoreCase("Success"))
			return "delete";
		else 
			return "false";
	}
}
