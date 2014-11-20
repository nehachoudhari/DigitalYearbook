package bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author nhchdhr
 * 
 * This class represents the Photograph entity and provides getters and setters for its fields
 * Student entity is mapped to Photograph table in the database 
 * Since there multiple photographs for a department/event and unique photograph for a student and committee member
 * Each photograph object has two more fields to map multiple photos to the same department/event
 * "TYPE" : This field gives the type of Entity (Student/Department etc)
 * "TYPE_ID" : Unique id of this entity object (buck id for student etc)
 */
@Entity
@Table(name="PHOTOGRAPH")
public class Photograph {
	
	@Id
	@GeneratedValue
	@Column(name="PHOTO_ID")
	private long photoId;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="TYPE_ID")
	private long typeId;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="DETAILS")
	private String details;
	
	public long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public void print()
	{
		System.out.println(details);
	}
}
