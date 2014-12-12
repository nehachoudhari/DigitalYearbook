package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import service.StudentService;
import serviceImpl.StudentServiceImpl;

/**
 * This is buck id validator which is called on Ajax call from registerStudent.xhtml page
 * @author admin
 *
 */
@FacesValidator("buckIdValidator")
public class BuckIdValidator implements Validator {

	@EJB
	StudentService studentService = new StudentServiceImpl();
	
    /**
     * Validates if the Buck Id already exists!
     * If it exists, throws an exception, else does nothing.
     * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        	System.out.println("Inside validate method of buckidvalidator");
        long buckId = (long)value;
        System.out.println("buck id "+buckId);
    	if(buckId != 0){
    		System.out.println("Inside if ");
    		System.out.println(studentService);
			 Student student = (Student)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("studentBean");
			 System.out.println(student);
			 boolean isValid = student.validateBuckIds(buckId);
			 if(!isValid){
				 System.out.println("Buck id already exists!!");
				 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						 "This buck id is already registered here.", null));
			 }
		 }
    }

}