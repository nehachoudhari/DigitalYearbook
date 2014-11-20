package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import bean.Department;
import bean.Photograph;
import constants.SqlCommands;
import constants.PhotoTypeEnum;
import dao.DepartmentDao;
import exception.YearbookException;

public class DepartmentDaoImpl extends ParentAbstractDao implements DepartmentDao{
	
	public int addDepartment(Department department, List<Photograph> photoList)
	throws YearbookException{
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		int deptId = -1;
		try{
			con = getConnection();
			//con.setAutoCommit(false);
			
			stmt = con.createStatement();
			stmt.execute(SqlCommands.ADD_DEPARTMENT_TABLE);
			
			String query = SqlCommands.ADD_DEPARTMENT;
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, department.getName());
			pstmt.setString(2, department.getMission());
			pstmt.setString(3, department.getLocation());
			pstmt.setString(4, department.getUrl());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
            if(rs != null && rs.next()){
            	deptId = rs.getInt(1);
            }
            if(deptId != -1){
            	PhotographDaoImpl photoDao = new PhotographDaoImpl();
    			for(int index=0; index<photoList.size(); index++){
    				Photograph photo = photoList.get(index);
    				photo.setType(PhotoTypeEnum.DEPARTMENT.toString());
    				photo.setTypeId(deptId);
    				photoDao.addPhoto(photo, con);
    			}
            }else{
            	throw new YearbookException();
            }			
			//con.commit();
		}catch (SQLException e) {
			if(e.getMessage().toUpperCase().contains("UNIQUE INDEX OR PRIMARY KEY VIOLATION:")) {
			   throw new YearbookException("Department with this department ID already exists.");
			   
			}
			e.printStackTrace();
		}catch(Exception e){
			System.out.println(e.getMessage()+e.getCause());
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
		return deptId;
	}

	
	public void deleteDepartment(Department department)
			throws YearbookException {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try{
			con = getConnection();
			con.setAutoCommit(false);
			
			stmt = con.createStatement();
			stmt.execute(SqlCommands.ADD_DEPARTMENT_TABLE);

			PhotographDaoImpl photoDao = new PhotographDaoImpl();
			Photograph photo = new Photograph();
			photo.setType(PhotoTypeEnum.DEPARTMENT.toString());
			photo.setTypeId(department.getDeptId());
			int result = photoDao.deletePhotos(photo, con);
			
			String query = SqlCommands.DELETE_DEPARTMENT;
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, department.getDeptId());
			result += pstmt.executeUpdate();
			if(result == 0){
				throw new YearbookException("Incorrect details for department delete..");
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

	
	public void updateDepartment(Department department,
			List<Photograph> photoList) throws YearbookException {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try{
			con = getConnection();
			con.setAutoCommit(false);
			
			stmt = con.createStatement();
			stmt.execute(SqlCommands.ADD_DEPARTMENT_TABLE);
			
			
			String query = SqlCommands.UPDATE_DEPARTMENT;
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, department.getName());
			pstmt.setString(2, department.getMission());
			pstmt.setString(3, department.getLocation());
			pstmt.setString(4, department.getUrl());
			pstmt.setInt(5, department.getDeptId());
			int result = pstmt.executeUpdate();
			
                        
            PhotographDaoImpl photoDao = new PhotographDaoImpl();
            Photograph photo = new Photograph();
            photo.setType(PhotoTypeEnum.DEPARTMENT.toString());
            photo.setTypeId(department.getDeptId());
            photoDao.deletePhotos(photo, con);
            
			for(int index=0; index<photoList.size(); index++){
				photo = photoList.get(index);
				photo.setType(PhotoTypeEnum.DEPARTMENT.toString());
				photo.setTypeId(department.getDeptId());
				photoDao.addPhoto(photo, con);
				result++;
			}
         
			if(result == 0){
				throw new YearbookException("Incorrect details for department update..");
			}
			con.commit();
		}catch(YearbookException e){
			throw e;
		}catch (SQLException e) {
			if(e.getMessage().toUpperCase().contains("DUPLICATE TABLE NAME")) {
				   throw new YearbookException("Department already exists... ");
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
}
