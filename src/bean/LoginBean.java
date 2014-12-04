/**
 * 
 */
package bean;

import javax.ejb.EJB;

import exception.YearbookException;
import service.StudentService;

/**
 * @author admin
 * Login bean for handling logging of students.
 *
 */
public class LoginBean
{
	@EJB 
	private StudentService studentService;
    private String username;
    public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	private String password;
	


    public String getPassword ()
    {
        return password;
    }


    public void setPassword (final String password)
    {
        this.password = password;
    }

    public String logStudent() throws YearbookException{
    	boolean result = studentService.login(username,password);
    	System.out.println("Output of result "+result);
    	if(result){
    		return "true";
    	}else{
    		return "false";
    	}
    }
}
