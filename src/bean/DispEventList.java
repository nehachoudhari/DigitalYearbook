package bean;

import helper.DropboxUploaderHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import service.EventService;
import exception.YearbookException;

public class DispEventList {
	
	@EJB
	EventService eventService;

	List<SelectItem> allEvents = null;
	
	List<Event> eventList = null;
	
	private StreamedContent listImage;
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		System.out.println("Event Id "+id);
	}

	public StreamedContent getListImage() {
		//FacesContext context = FacesContext.getCurrentInstance();
        //HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        //String imageID = (String) myRequest.getParameter("imageID");
		 FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		 HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance()
                 .getExternalContext().getRequest();
		 Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		 FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().entrySet();
		 String param = parameterMap.get("id");
		System.out.println(id);
        StreamedContent img = null;
        for (Event e : eventList){
        	if(e.getEventId() == id){
        		img = e.getDbImage();
        	}
        }
        return img;
	}

	public void setListImage(StreamedContent listImage) {
		this.listImage = listImage;
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public List<SelectItem> getAllEvents() {
		return allEvents;
	}

	public void setAllEvents(List<SelectItem> allEvents) {
		this.allEvents = allEvents;
	}

	private StreamedContent dbImage;
	
	public StreamedContent getDbImage() {
		return dbImage;
	}
	public void setDbImage(StreamedContent dbImage) {
		this.dbImage = dbImage;
	}
	
	private Event convertToBean(entity.Event entityBean){
		Event event = new Event();
		try {			
			event.setDate(entityBean.getDate());
			event.setDescription(entityBean.getDescription());
			event.setEventId(entityBean.getEventId());
			event.setName(entityBean.getName());
			event.setUrl(entityBean.getUrl());
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			dropboxUploader.fetchFromDropBoxIntoYearbook(entityBean.getPhotoUrl());
			ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//images//yearbook"+entityBean.getPhotoUrl());
			File dispFile = new File(filePath);
			StreamedContent dbImage = null;
			dbImage = new DefaultStreamedContent(new FileInputStream(dispFile), "image/jpg");
			event.setDbImage(dbImage);
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return event;
	}
	
	
	//@PostConstruct
	public String init() {
		allEvents = new ArrayList<SelectItem>();
		Collection<entity.Event> list;
		try {
			list = eventService.getAllEvents();
			if(list!= null) {
				eventList = new ArrayList<Event>();
				for(entity.Event e : list) {
					allEvents.add(new SelectItem(e.getEventId(),e.getName()));
					eventList.add(convertToBean(e));		
				
				}
			}
			else{
				System.out.println("No events found");
			}
			return "event";
		} catch (YearbookException e) {
			e.printStackTrace();
		}
		return "false";
    }
}
