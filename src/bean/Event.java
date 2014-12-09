package bean;

import java.io.File;
import java.io.FileInputStream;

import helper.DropboxUploaderHelper;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import service.EventService;
import exception.YearbookException;

/**
 * @author nhchdhr
 * 
 * This class represents the Event entity and provides getters and setters for its fields
 * This entity is mapped to Event table in the database 
 *
 */
public class Event extends Photograph{
	@EJB
	EventService eventService;
	
	private String name;
	private String description;
	private String date;
	private String url;
	private long eventId;
	private String photoUrl;
	private String selectedEvId;
	private StreamedContent dbImage;
	
	public StreamedContent getDbImage() {
		return dbImage;
	}
	public void setDbImage(StreamedContent dbImage) {
		this.dbImage = dbImage;
	}
	
	public String getSelectedEvId() {
		return selectedEvId;
	}
	public void setSelectedEvId(String selectedEvId) {
		this.selectedEvId = selectedEvId;
	}
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
		try{
			System.out.println("Inside add");
			System.out.println("Inside add 1 - "+this.file);
			System.out.println(this.file.getFileName());
			copyFile(this.file.getFileName(), this.file.getInputstream(), "Event");
	
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			String dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Event");
			System.out.println("Dropbox "+dropboxUrl);
			boolean ret = eventService.addEvent(name, description, date, url, dropboxUrl);
			
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	public String modify() throws YearbookException{
		try{
			System.out.println("Inside modify");
			System.out.println(this.file.getFileName());
			String dropboxUrl = null;
			if(this.file!=null){
				System.out.println(this.file.getFileName());
				copyFile(this.file.getFileName(), this.file.getInputstream(),"Event");
			
				DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
				dropboxUrl = dropboxUploader.uploadToDropBox(this.file.getFileName(), "Event");
				System.out.println("Dropbox "+dropboxUrl);
			}
			boolean ret = eventService.updateEvent(eventId,name, description, date, url, dropboxUrl);
			
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	public String delete() throws YearbookException{
		try{
			System.out.println("Inside delete event ");
			
			boolean ret = eventService.deleteEvent(eventId);
			
			if(ret)
					return "true";
				else 
					return "false";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
	}
	
	public String showEvent() throws YearbookException{
		try {
			System.out.println("Inside member list");
			System.out.println("Event id  - "+selectedEvId);
			entity.Event event = eventService.getEvent(Long.parseLong(selectedEvId));
			this.date = event.getDate();
			this.description = event.getDescription();
			this.eventId = event.getEventId();
			this.name = event.getName();
			this.url = event.getUrl();

			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			dropboxUploader.fetchFromDropBox(event.getPhotoUrl());

	    	ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//images//downloads"+event.getPhotoUrl());
			System.out.println(filePath);
			File dispFile = new File(filePath);
			dbImage = null;
			dbImage = new DefaultStreamedContent(new FileInputStream(dispFile), "image/jpg");
			this.photoUrl = filePath;
		} catch (YearbookException e) {
			e.printStackTrace();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "true";
    }

	public boolean equals(Object other)
	{
	    return other instanceof Event && (selectedEvId != null) ? selectedEvId.equals(((Event) other).getEventId()) : (other == this);
	}

	public int hashCode()
	{
	    return selectedEvId != null ? this.getClass().hashCode() + selectedEvId.hashCode() : super.hashCode();
	}
}
