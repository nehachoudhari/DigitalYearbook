package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import bean.Photograph;
import constants.Constants;
import dao.PhotographDao;

public class PhotographDaoImpl implements PhotographDao{
	public void addPhoto(Photograph photo, Connection con){
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try{
			stmt = con.createStatement();
			stmt.execute(Constants.ADD_PHOTO_TABLE);
			
			String query = Constants.ADD_PHOTO;
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, photo.getType());
			pstmt.setLong(2, photo.getTypeId());
			pstmt.setString(3, photo.getUrl());
			pstmt.setString(4, photo.getDetails());
			
			//comment this when photo id is auto-generated.
			pstmt.setLong(5, photo.getPhotoId());
			
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(con!=null){
				try{
					stmt.close();
					pstmt.close();
					stmt = null;
					pstmt = null;
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
	}
	
	public int updatePhoto(Photograph photo, Connection con){
		Statement stmt = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try{
			stmt = con.createStatement();
			stmt.execute(Constants.ADD_PHOTO_TABLE);
			
			String query = Constants.UPDATE_PHOTO;
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, photo.getUrl());
			pstmt.setString(2, photo.getDetails());
			pstmt.setLong(3, photo.getPhotoId());
			
			result = pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(con!=null){
				try{
					stmt.close();
					pstmt.close();
					stmt = null;
					pstmt = null;
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
		return result;
	}
	
	public int deletePhotos(Photograph photo, Connection con){
		Statement stmt = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try{
			stmt = con.createStatement();
			stmt.execute(Constants.ADD_PHOTO_TABLE);
			
			String query = Constants.DELETE_PHOTO;
			pstmt = con.prepareStatement(query);
			pstmt.setLong(1, photo.getPhotoId());
			
			result = pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(con!=null){
				try{
					stmt.close();
					pstmt.close();
					stmt = null;
					pstmt = null;
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
		return result;
	}
}
