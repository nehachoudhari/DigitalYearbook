package bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nhchdhr
 * 
 * This class represents the Event entity and provides getters and setters for its fields
 * Student entity is mapped to Event table in the database 
 *
 */
@Entity
@Table(name="EVENT")
public class Event {
	/**
	 * CREATE TABLE IF NOT EXISTS EVENT(EVENT_ID INTEGER AUTO_INCREMENT, NAME VARCHAR2(50) 
	 * NOT NULL UNIQUE , EVENT_DATE VARCHAR2(10), URL VARCHAR2(200), DETAILS VARCHAR2(100), PRIMARY KEY (EVENT_ID))";
	 */
	
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DETAILS")
	private String description;
	
	@Column(name="EVENT_DATE")
	private String date;
	
	@Column(name="URL")
	private String url;
	
	@Id
	@GeneratedValue
	@Column(name="EVENT_ID")
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
