package daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

abstract class ParentAbstractDao {
	
	public Connection getConnection() throws Exception {
		Connection conn = null;
		try{
        	Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/yearbook", "sa", "");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE USER IF NOT EXISTS YearbookDB PASSWORD 'yearbookdb'");
        	stmt.execute("GRANT ALTER ANY SCHEMA TO YearbookDB");
        	stmt.close() ;
            conn.close();
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/yearbook", "YearbookDB", "yearbookdb");
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }
        return conn;
	}

}
