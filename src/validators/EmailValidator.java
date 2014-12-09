package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator{

  static String emailRegex = "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
  static Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
  static Matcher matcher;

  public EmailValidator() {
    log("EmailValidator Constructor");
  }

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    log("Validating submitted email -- " + value.toString());
    matcher = pattern.matcher(value.toString());
    
    if (!matcher.matches()) {
//      context.addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
//              "Enter valid email Id."));

      FacesMessage msg =
              new FacesMessage(" E-mail validation failed.",
              "Please enter an E-mail address in this format: abcd@abc.com");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      
      throw new ValidatorException(msg);
    }
  }

  private void log(String msg) {
    System.out.println(msg);

  }
}
