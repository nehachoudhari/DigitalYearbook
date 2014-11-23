package bean;

import java.io.File;


public class Photograph {
		
	private String type;
	
	private long typeId;
	
	private File file;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

	private String details;
	
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
