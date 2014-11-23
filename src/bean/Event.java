package bean;

/**
 * @author nhchdhr
 * 
 * This class represents the Event entity and provides getters and setters for its fields
 * This entity is mapped to Event table in the database 
 *
 */
public class Event {
	
	private String name;
	private String description;
	private String date;
	private String url;
	private long eventId;
	
	
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
}
