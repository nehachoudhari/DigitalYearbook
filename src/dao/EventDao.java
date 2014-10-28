package dao;

import java.util.List;

import exception.YearbookException;

import bean.Event;
import bean.Photograph;

public interface EventDao{
	
	public void addEvent(Event event, List<Photograph> photoList) throws YearbookException;
	
	public void updateEvent(Event event, List<Photograph> photoList) throws YearbookException;
	
	public void deleteEvent(Event event) throws YearbookException;
}
