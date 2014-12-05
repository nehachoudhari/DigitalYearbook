package bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import service.EventService;
import exception.YearbookException;

public class EventList {
	
	@EJB
	EventService eventService;

	List<SelectItem> allEvents = null;
	
	List<entity.Event> eventList = null;
	
	
	public List<entity.Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<entity.Event> eventList) {
		this.eventList = eventList;
	}

	public List<SelectItem> getAllEvents() {
		return allEvents;
	}

	public void setAllEvents(List<SelectItem> allEvents) {
		this.allEvents = allEvents;
	}


	@PostConstruct
	public void init() {
		allEvents = new ArrayList<SelectItem>();
		Collection<entity.Event> list;
		try {
			list = eventService.getAllEvents();
			if(list!= null) {
				eventList = new ArrayList<entity.Event>(list);
				for(entity.Event e : list) {
					allEvents.add(new SelectItem(e.getEventId(),e.getName()));
				}
			}
			else{
				System.out.println("No events found");
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
}
