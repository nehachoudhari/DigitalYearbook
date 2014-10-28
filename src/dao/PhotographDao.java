package dao;

import java.sql.Connection;

import bean.Photograph;

public interface PhotographDao {
	public void addPhoto(Photograph photo, Connection con);
	
	public int  updatePhoto(Photograph photo, Connection con);
	
	public int deletePhotos(Photograph photo, Connection con);
}
