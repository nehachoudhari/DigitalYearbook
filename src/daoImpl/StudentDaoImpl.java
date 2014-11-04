package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import constants.Constants;
import constants.PhotoTypeEnum;
import dao.StudentDao;
import exception.YearbookException;

import bean.Photograph;
import bean.Student;

public class StudentDaoImpl extends ParentAbstractDao implements StudentDao{
	public void addStudent(Student student, Photograph photo) throws YearbookException{
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try{
			con = getConnection();
			//con.setAutoCommit(false);
			
			stmt = con.createStatement();
			stmt.execute(Constants.ADD_STUDENT_TABLE);
			
			String query = Constants.ADD_STUDENT;
			pstmt = con.prepareStatement(query);
			pstmt.setLong(1, student.getBuckId());
			pstmt.setString(2, student.getFirstName());
			pstmt.setString(3, student.getLastName());
			pstmt.setString(4, student.getGradYear());
			pstmt.setString(5, student.getDob());
			pstmt.setString(6, student.getJobInternDetails());
			pstmt.setString(7, student.getContactNumber());
			pstmt.setInt(8, student.getDeptId());
			pstmt.setString(9, student.getUsername());
			pstmt.setString(10, student.getPassword());
			pstmt.executeUpdate();
			
			photo.setType(PhotoTypeEnum.STUDENT.toString());
			photo.setTypeId(student.getBuckId());
			PhotographDaoImpl photoDao = new PhotographDaoImpl();
			photoDao.addPhoto(photo, con);
						
			//con.commit();
		}catch (SQLException e) {
			if(e.getMessage().toUpperCase().contains("DUPLICATE TABLE NAME")) {
				   throw new YearbookException("Duplicate Student buck id.");
			}else{
				System.out.println(e.getMessage());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(con!=null){
				try{
					stmt.close();
					pstmt.close();
					con.close();
					con=null;
					stmt = null;
					pstmt = null;
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
	}
	
	public void updateStudent(Student student, Photograph photo) throws YearbookException{
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try{
			con = getConnection();
			con.setAutoCommit(false);
			
			stmt = con.createStatement();
			stmt.execute(Constants.ADD_STUDENT_TABLE);
			
			String query = Constants.UPDATE_STUDENT;
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, student.getFirstName());
			pstmt.setString(2, student.getLastName());
			pstmt.setString(3, student.getGradYear());
			pstmt.setString(4, student.getDob());
			pstmt.setString(5, student.getJobInternDetails());
			pstmt.setString(6, student.getContactNumber());
			pstmt.setInt(7, student.getDeptId());
			pstmt.setString(8, student.getPassword());
			pstmt.setLong(9, student.getBuckId());
			int result = pstmt.executeUpdate();
			
			photo.setType(PhotoTypeEnum.STUDENT.toString());
			photo.setTypeId(student.getBuckId());
			PhotographDaoImpl photoDao = new PhotographDaoImpl();
			result = result + photoDao.updatePhoto(photo, con);
						
			con.commit();
			if(result == 0){
				throw new YearbookException("Incorrect details for student update..");
			}
			con.commit();
		}catch(YearbookException e){
			throw e;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(con!=null){
				try{
					stmt.close();
					pstmt.close();
					con.close();
					con=null;
					stmt = null;
					pstmt = null;
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
	}
	
	public void deleteStudent(Student student) throws YearbookException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = getConnection();
			con.setAutoCommit(false);
			
			Photograph photo = new Photograph();
			photo.setType(PhotoTypeEnum.STUDENT.toString());
			photo.setTypeId(student.getBuckId());
			PhotographDaoImpl photoDao = new PhotographDaoImpl();
			photoDao.deletePhotos(photo, con);
			
			String query = Constants.DELETE_STUDENT;
			pstmt = con.prepareStatement(query);
			pstmt.setLong(1, student.getBuckId());
			int result = pstmt.executeUpdate();
			if(result == 0){
				throw new YearbookException("Incorrect details for student delete..");
			}
			con.commit();
		}catch(YearbookException e){
			throw e;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(con!=null){
				try{
					pstmt.close();
					con.close();
					con=null;
					pstmt = null;
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
	}
}
